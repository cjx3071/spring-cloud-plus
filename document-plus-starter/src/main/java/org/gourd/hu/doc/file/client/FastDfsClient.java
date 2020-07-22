package org.gourd.hu.doc.file.client;

import com.github.tobato.fastdfs.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author gourd.hu
 */
@Component
@Slf4j
public class FastDfsClient {
 

    private static FastFileStorageClient fastFileStorageClient;
 
    private static FdfsWebServer fdfsWebServer;
 
    @Autowired
    public void setFastDFSClient(FastFileStorageClient fastFileStorageClient, FdfsWebServer fdfsWebServer) {
        FastDfsClient.fastFileStorageClient = fastFileStorageClient;
        FastDfsClient.fdfsWebServer = fdfsWebServer;
    }
 
    /**
     * @param multipartFile 文件对象
     * @return 返回文件地址
     * @description 上传文件
     */
    public static String uploadFile(MultipartFile multipartFile) {
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            return storePath.getFullPath();
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
 
    /**
     * @param multipartFile 图片对象
     * @return 返回图片地址
     * @description 上传缩略图
     */
    public static String uploadImageAndCrtThumbImage(MultipartFile multipartFile) {
        try {
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
 
    /**
     * @param file 文件对象
     * @return 返回文件地址
     * @description 上传文件
     */
    public static String uploadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
 
    /**
     * @param file 图片对象
     * @return 返回图片地址
     * @description 上传缩略图
     */
    public static String uploadImageAndCrtThumbImage(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
 
    /**
     * @param bytes         byte数组
     * @param fileExtension 文件扩展名
     * @return 返回文件地址
     * @description 将byte数组生成一个文件上传
     */
    public static String uploadFile(byte[] bytes, String fileExtension) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        StorePath storePath = fastFileStorageClient.uploadFile(stream, bytes.length, fileExtension, null);
        return storePath.getFullPath();
    }
 
    /**
     * @param fileUrl 文件访问地址
     * @description 下载文件
     */
    public static boolean downloadFile(String fileUrl) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            String[] splits = StringUtils.split(fileUrl, "/");
            // 文件名
            String filename = splits[splits.length-1];
            byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            // 写入到流
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes);
            out.close();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
 
    /**
     * @param fileUrl 文件访问地址
     * @description 删除文件
     */
    public static boolean deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.praseFromUrl(fileUrl);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 封装文件完整URL地址
     * @param path
     * @return
     */
    public static String getResAccessUrl(String path) {
        String url = fdfsWebServer.getWebServerUrl() + path;
        log.info("上传文件地址为：\n" + url);
        return url;
    }
 
}