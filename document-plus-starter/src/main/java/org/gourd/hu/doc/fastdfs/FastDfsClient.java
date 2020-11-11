package org.gourd.hu.doc.fastdfs;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.domain.upload.FastImageFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 *
 * fastdfs 客户端类
 * @author gourd.hu
 */
@Slf4j
public class FastDfsClient {
 

    private static FastFileStorageClient fastFileStorageClient;
 
    private static FdfsWebServer fdfsWebServer;

    /**
     * 组名称
     */
    private static String groupName;

    @Value("${fdfs.group-name:cloudPlus}")
    private String group;
 
    @Autowired
    public void setFastDFSClient(FastFileStorageClient fastFileStorageClient, FdfsWebServer fdfsWebServer) {
        FastDfsClient.fastFileStorageClient = fastFileStorageClient;
        FastDfsClient.fdfsWebServer = fdfsWebServer;
        FastDfsClient.groupName = group;
    }
 
    /**
     * @param multipartFile 文件对象
     * @return 返回文件地址
     * @description 上传文件
     */
    public static String uploadFile(MultipartFile multipartFile) {
        try {
            FastFile.Builder builder = new FastFile.Builder();
            builder.withFile(multipartFile.getInputStream(),multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            builder.toGroup(groupName);
            FastFile fastFile = builder.build();
            StorePath storePath = fastFileStorageClient.uploadFile(fastFile);
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
    public static String uploadImage(MultipartFile multipartFile) {
        try {
            FastImageFile.Builder builder = new FastImageFile.Builder();
            builder.withFile(multipartFile.getInputStream(),multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            builder.toGroup(groupName);
            FastImageFile fastImageFile = builder.build();
            StorePath storePath = fastFileStorageClient.uploadImage(fastImageFile);
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
            FastFile.Builder builder = new FastFile.Builder();
            builder.withFile(inputStream,file.length(), FilenameUtils.getExtension(file.getName()));
            builder.toGroup(groupName);
            FastFile fastFile = builder.build();
            StorePath storePath = fastFileStorageClient.uploadFile(fastFile);
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
    public static String uploadImage(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            FastImageFile.Builder builder = new FastImageFile.Builder();
            builder.withFile(inputStream,file.length(), FilenameUtils.getExtension(file.getPath()));
            builder.toGroup(groupName);
            FastImageFile fastImageFile = builder.build();
            StorePath storePath = fastFileStorageClient.uploadImage(fastImageFile);
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
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FastFile.Builder builder = new FastFile.Builder();
        builder.withFile(inputStream,bytes.length,fileExtension);
        builder.toGroup(groupName);
        FastFile fastFile = builder.build();
        StorePath storePath = fastFileStorageClient.uploadFile(fastFile);
        return storePath.getFullPath();
    }
 
    /**
     * @param fileUrl 文件访问地址
     * @description 下载文件
     */
    public static boolean downloadFile(String fileUrl) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        ServletOutputStream out = null;
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            String[] splits = StringUtils.split(fileUrl, "/");
            // 文件名
            String filename = splits[splits.length-1];
            byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            // 写入到流
            out = response.getOutputStream();
            out.write(bytes);
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }finally {
            if(out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("文件下载失败：",e);
                }
            }
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
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
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