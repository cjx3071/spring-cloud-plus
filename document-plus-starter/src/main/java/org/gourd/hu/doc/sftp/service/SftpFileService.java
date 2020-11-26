package org.gourd.hu.doc.sftp.service;

import java.io.File;
import java.io.InputStream;

/**
 * 文件服务
 *
 * @author wb
 */
public interface SftpFileService {

    /**
     * 上传文件
     * @param targetPath
     * @param inputStream
     * @return
     * @throws Exception
     */
    boolean uploadFile(String targetPath, InputStream inputStream) throws Exception;

    /**
     * 上传文件
     * @param targetPath
     * @param file
     * @return
     * @throws Exception
     */
    boolean uploadFile(String targetPath, File file) throws Exception;

    /**
     * 下载文件
     * @param targetPath
     * @return
     * @throws Exception
     */
    File downloadFile(String targetPath) throws Exception;

    /**
     * 删除文件
     * @param targetPath
     * @return
     * @throws Exception
     */
    boolean deleteFile(String targetPath) throws Exception;
}