package org.gourd.hu.doc.utils;

import com.lowagie.text.xml.xmp.XmpWriter;
import gui.ava.html.Html2Image;
import gui.ava.html.renderer.ImageRenderer;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

/**
 * @Description html模板转jpg
 * @Author gourd.hu
 * @Date 2020/6/28 10:43
 * @Version 1.0
 */
@Slf4j
public class ImageUtil {

    /**
     * 保存到本地特定路径下
     *
     * @param templateEngine
     * @param templateName
     * @param variables
     * @param filePath
     */
    public static void saveAsImage(TemplateEngine templateEngine, String templateName, Map<String,Object> variables, String filePath){
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        //imageHtml为获取的html源码字符串
        String imageHtml = templateEngine.process(templateName, context);
        Html2Image html2Image = Html2Image.fromHtml(imageHtml);
        ImageRenderer imageRenderer = html2Image.getImageRenderer();
        imageRenderer.saveImage(filePath);
    }

    /**
     * 以文件流形式下载到浏览器
     *
     * @param templateEngine
     * @param templateName
     * @param variables
     * @param response
     * @param fileName
     */
    public static void download(TemplateEngine templateEngine, String templateName, Map<String,Object> variables, HttpServletResponse response, String fileName ){
        // 断言参数不为空
        ResponseEnum.TEMPLATE_DATA_NULL.assertNotEmpty(variables);
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        // 设置编码、文件ContentType类型、文件头、下载文件名
        response.setCharacterEncoding(XmpWriter.UTF8);
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
            outputStream = response.getOutputStream();
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        //imageHtml为获取的html源码字符串
        String imageHtml = templateEngine.process(templateName, context);
        Html2Image html2Image = Html2Image.fromHtml(imageHtml);
        ImageRenderer imageRenderer = html2Image.getImageRenderer();
        imageRenderer.saveImage(outputStream,Boolean.TRUE);
    }

    /**
     * 获取到输入流
     *
     * @param templateEngine
     * @param templateName
     * @param variables
     * @return
     */
    public static InputStream getImageInputStream(TemplateEngine templateEngine, String templateName, Map<String,Object> variables,String fileType){
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        //imageHtml为获取的html源码字符串
        String imageHtml = templateEngine.process(templateName, context);
        Html2Image html2Image = Html2Image.fromHtml(imageHtml);
        ImageRenderer imageRenderer = html2Image.getImageRenderer();
        BufferedImage bufferedImage = imageRenderer.getBufferedImage();
        return bufferedImageToInputStream(bufferedImage,fileType);
    }

    /**
     * 将BufferedImage转换为InputStream
     * @param image
     * @return
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image,String fileType){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, fileType, os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            log.error("获取输入流失败:",e);
        }
        return null;
    }
}
