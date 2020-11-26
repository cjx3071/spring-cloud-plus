package org.gourd.hu.doc.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.gourd.hu.doc.fastdfs.FastDfsClient;
import org.gourd.hu.doc.ftp.FtpUtil;
import org.gourd.hu.doc.minio.utils.MinIoUtil;
import org.gourd.hu.doc.openoffice.utils.FileConvertUtil;
import org.gourd.hu.doc.sftp.service.impl.SftpFileServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @author gourd.hu
 * @date 2018-11-20
 */
@Configuration
@Import({FdfsClientConfig.class, FileConvertUtil.class, FastDfsClient.class, MinIoUtil.class, FtpUtil.class, SftpFileServiceImpl.class})
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FileAutoConfig {

}
