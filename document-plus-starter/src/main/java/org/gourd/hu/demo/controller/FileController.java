package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.doc.fastdfs.FastDfsClient;
import org.gourd.hu.doc.minio.utils.MinIoUtil;
import org.gourd.hu.doc.sftp.service.SftpFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * @author gourd
 */
@RestController
@RequestMapping("/file")
@Api(tags = "file测试API", description = "文件控制器")
public class FileController {

    /*==========================以下为fastDfs文件上传、下载===========================*/


    /**
     * fastDfs上传文件
     *
     */
    @PostMapping("/fast-file")
    @ApiOperation(value="fastDfs上传文件")
    public BaseResponse fastDfsFile(MultipartFile file){
        String filePath = FastDfsClient.uploadFile(file);
        if(StringUtils.isNotEmpty(filePath)){
            String resAccessUrl = FastDfsClient.getResAccessUrl(filePath);
            return BaseResponse.ok("文件上传成功",resAccessUrl);
        }else {
            return  BaseResponse.fail("文件上传失败");
        }
    }

    /**
     * fastDfs上传图片
     *
     */
    @PostMapping("/fast-image")
    @ApiOperation(value="fastDfs上传图片")
    public BaseResponse fastDfsImage(MultipartFile file){
        String filePath = FastDfsClient.uploadImage(file);
        if(StringUtils.isNotEmpty(filePath)){
            String resAccessUrl = FastDfsClient.getResAccessUrl(filePath);
            return BaseResponse.ok("图片上传成功",resAccessUrl);
        }else {
            return  BaseResponse.fail("图片件上传失败");
        }
    }
    /**
     * fastDfs下载文件
     *
     */
    @GetMapping("/fast-file")
    @ApiOperation(value="fastDfs下载文件")
    public void fastDfsFile(@RequestParam String filePath){
        FastDfsClient.downloadFile(filePath);
    }


    /**
     * 文件删除
     *
     * @param path
     * @return
     */
    @ApiOperation("文件删除")
    @DeleteMapping("/delete")
    public BaseResponse delete(@RequestParam String path) {

        // 第一种删除：参数：完整地址
        FastDfsClient.deleteFile(path);

        // 第二种删除：参数：组名加文件路径
        // fastFileStorageClient.deleteFile(group,path);

        return BaseResponse.ok("文件删除成功");
    }

    /**
     * minio上传文件
     *
     */
    @PostMapping("/minio-upload")
    @ApiOperation(value="minio上传文件")
    public BaseResponse minioUpload(MultipartFile file){
        return BaseResponse.ok(MinIoUtil.uploadObject(file,"gourd","suzhou"));
    }
    /**
     * minio下载文件
     *
     */
    @GetMapping("/minio-download")
    @ApiOperation(value="minio下载文件")
    public void minioDownload(){
        MinIoUtil.downloadObject("gourd","suzhou","paixu.gif");
    }

    @Autowired
    private SftpFileService sftpFileService;

    @PostMapping("/sftp-upload")
    @ApiOperation(value="sftp上传文件")
    public void sftpUpload() throws Exception {
        File file = new File("D:\\xxx.pdf");
        InputStream inputStream = new FileInputStream(file);

        boolean uploadFile = sftpFileService.uploadFile("document/4c392-34wsd/34/ID/" + file.getName(), inputStream);
        if (uploadFile) {
            System.out.println("success.....");
        } else {
            System.out.println("failure.....");
        }

        inputStream.close();
    }

    @PostMapping("/sftp-download")
    @ApiOperation(value="sftp下载文件")
    public void download() throws Exception {
        File file = sftpFileService.downloadFile("xxx.pdf");
        if (file == null) {
            throw new FileNotFoundException("File not found!");
        }
        System.out.println(file.getName());

        file.delete();
    }


}