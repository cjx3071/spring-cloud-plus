package org.gourd.hu.doc.openoffice.utils;


import lombok.extern.slf4j.Slf4j;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;



/**
 * 文件转换工具类
 * @author gourd
 * 2017-02-15 13:19:03
 */
@Slf4j
public class OfficeConvertToPdf {


    /**
     * 转换文件
     * @param inputFile
     * @param outputFilePath
     * @param fileName
     */
    public static Boolean converterFiles(File inputFile, String outputFilePath, String fileName) {
        boolean flag = false ;
        //启动OpenOffice.org服务
        OfficeManager officeManager = getOfficeManager();
        // 连接OpenOffice
        OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);

        File outputFile = new File(outputFilePath);
        converter.convert(inputFile, outputFile);
        if(officeManager != null){
            outputFile = new File(outputFilePath);
            if (outputFile.exists()) {
                flag = true ;
                officeManager.stop();
                // 假如目标路径不存在,则新建该路径
                log.info("关闭openOffice服务");
            }
        }
       log.info("文件：" + fileName + "转换为目标文件：" + outputFile + "成功！");
        return flag ;
    }

    /**
     * 启动OpenOffice.org服务
     * @return
     */
    public static  OfficeManager getOfficeManager() {
        OfficeManager officeManager = null ;

        log.info("openOffice准备启动");
        DefaultOfficeManagerConfiguration config = new DefaultOfficeManagerConfiguration();
        // 获取OpenOffice.org的安装目录
        String officeHome = getOfficeHome();
        config.setOfficeHome(officeHome);

//        config.setPipeName("6080") ;
        // 设置任务执行超时为5分钟
        config.setTaskExecutionTimeout(1000 * 60 * 5L);
        // 设置任务队列超时为24小时
        config.setTaskQueueTimeout(1000 * 60 * 60 * 24L);

        // 启动OpenOffice的服务
        officeManager = config.buildOfficeManager();
        officeManager.start();
        log.info("openOffice启动成功");
        return officeManager;
    }

    /**
     * 判断系统环境，并获得openOffice的安装目录
     * @return
     */
    public static String getOfficeHome() {
        String osName = System.getProperty("os.name");
        if (Pattern.matches("Linux.*", osName)) {
            return "/opt/openoffice.org3";
        } else if (Pattern.matches("Windows.*", osName)) {
            return "C:\\Program Files (x86)\\OpenOffice 4";
        } else if (Pattern.matches("Mac.*", osName)) {
            return "/Application/OpenOffice.org.app/Contents";
        }
        return null;
    }
}
