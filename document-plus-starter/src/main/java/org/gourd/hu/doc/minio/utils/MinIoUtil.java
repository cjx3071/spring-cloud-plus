package org.gourd.hu.doc.minio.utils;

import com.google.common.io.ByteStreams;
import com.lowagie.text.xml.xmp.XmpWriter;
import io.minio.*;
import io.minio.http.Method;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.doc.minio.properties.MinIoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * minio工具类
 *
 * @author gourd.hu
 */
@Configuration
@EnableConfigurationProperties({MinIoProperties.class})
public class MinIoUtil {

    private MinIoProperties minIo;

    public MinIoUtil(MinIoProperties minIo) {
        this.minIo = minIo;
    }

    private static MinioClient minioClient;

    @PostConstruct
    public void init() {
        try {
            minioClient = MinioClient.builder()
                    .endpoint(minIo.getEndpoint())
                    .credentials(minIo.getAccessKey(), minIo.getSecretKey())
                    .build();
        } catch (Exception e) {
            ResponseEnum.MIN_IO_INIT_FAIL.assertFail(e);
        }
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @return
     */
    public static boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_CHECK_FAIL.assertFail(e);
        }
        return false;
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName
     * @param region
     * @return
     */
    public static boolean bucketExists(String bucketName, String region) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).region(region).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_CHECK_FAIL.assertFail(e);
        }
        return false;
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     */
    public static void makeBucket(String bucketName) {
        try {
            boolean isExist = bucketExists(bucketName);
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_CREATE_FAIL.assertFail(e);
        }
    }

    /**
     * 创建 bucket
     *
     * @param bucketName
     * @param region
     */
    public static void makeBucket(String bucketName, String region) {
        try {
            boolean isExist = bucketExists(bucketName, region);
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).region(region).build());
            }
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_CREATE_FAIL.assertFail(e);
        }
    }

    /**
     * 删除 bucket
     *
     * @param bucketName
     * @return
     */
    public static void deleteBucket(String bucketName) {
        try {
            minioClient.deleteBucketEncryption(
                    DeleteBucketEncryptionArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_DELETE_FAIL.assertFail(e);
        }
    }

    /**
     * 删除 bucket
     *
     * @param bucketName
     * @return
     */
    public static void deleteBucket(String bucketName, String region) {
        try {
            minioClient.deleteBucketEncryption(
                    DeleteBucketEncryptionArgs.builder().bucket(bucketName).bucket(region).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_BUCKET_DELETE_FAIL.assertFail(e);
        }
    }


    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filePath
     */
    public static void uploadObject(String bucketName, String objectName, String filePath) {
        putObject(bucketName, null, objectName, filePath);
    }

    /**
     * 文件上传
     *
     * @param bucketName
     * @param objectName
     * @param filePath
     */
    public static void uploadObject(String bucketName, String region, String objectName, String filePath) {
        putObject(bucketName, region, objectName, filePath);
    }


    /**
     * 文件上传
     *
     * @param multipartFile
     * @param bucketName
     * @return
     */
    public static String uploadObject(MultipartFile multipartFile, String bucketName) {
        // bucket 不存在，创建
        if (!bucketExists(bucketName)) {
            makeBucket(bucketName);
        }
        return putObject(multipartFile, bucketName, null);
    }

    /**
     * 文件上传
     *
     * @param multipartFile
     * @param bucketName
     * @return
     */
    public static String uploadObject(MultipartFile multipartFile, String bucketName, String region) {
        // bucket 不存在，创建
        if (!bucketExists(bucketName, region)) {
            makeBucket(bucketName, region);
        }
        return putObject(multipartFile, bucketName, region);
    }

    /**
     * 文件下载
     *
     * @param bucketName
     * @param region
     * @param fileName
     * @return
     */
    public static void downloadObject(String bucketName, String region, String fileName) {
        HttpServletResponse response = RequestHolder.getResponse();
        // 设置编码
        response.setCharacterEncoding(XmpWriter.UTF8);
        try (ServletOutputStream os = response.getOutputStream();
             GetObjectResponse is = minioClient.getObject(
                     GetObjectArgs.builder()
                             .bucket(bucketName)
                             .region(region)
                             .object(fileName)
                             .build());) {

            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            ByteStreams.copy(is, os);
            os.flush();
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_DOWNLOAD_FAIL.assertFail(e);
        }
    }


    /**
     * 获取文件
     *
     * @param bucketName
     * @param region
     * @param fileName
     * @return
     */
    public static String getObjectUrl(String bucketName, String region, String fileName) {
        String objectUrl = null;
        try {
            objectUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .region(region)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_GET_FAIL.assertFail(e);
        }
        return objectUrl;
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param objectName
     */
    public static void deleteObject(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_DELETE_FAIL.assertFail(e);
        }
    }

    /**
     * 删除文件
     *
     * @param bucketName
     * @param region
     * @param objectName
     */
    public static void deleteObject(String bucketName, String region, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).region(region).object(objectName).build());
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_DELETE_FAIL.assertFail(e);
        }
    }


    /**
     * 上传文件
     *
     * @param multipartFile
     * @param bucketName
     * @return
     */
    private static String putObject(MultipartFile multipartFile, String bucketName, String region) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 上传文件的名称
            String fileName = multipartFile.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .region(region)
                    .object(fileName)
                    .stream(inputStream, multipartFile.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE)
                    .contentType(multipartFile.getContentType())
                    .build());
            // 返回访问路径
            return getObjectUrl(bucketName, region, fileName);
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_UPLOAD_FAIL.assertFail(e);
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param bucketName
     * @param region
     * @param objectName
     * @param filePath
     */
    private static String putObject(String bucketName, String region, String objectName, String filePath) {
        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .region(region)
                            .object(objectName)
                            .filename(filePath)
                            .build());
            // 返回访问路径
            return getObjectUrl(bucketName, region, objectName);
        } catch (Exception e) {
            ResponseEnum.MIN_IO_FILE_UPLOAD_FAIL.assertFail(e);
        }

        return null;
    }
}