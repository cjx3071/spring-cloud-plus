package org.gourd.hu.doc.utils;

import gui.ava.html.Html2Image;
import gui.ava.html.renderer.ImageRenderer;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Description html模板转jpg
 * @Author gourd.hu
 * @Date 2020/6/28 10:43
 * @Version 1.0
 */
@Slf4j
public class ImageUtil {

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

    public static void download(TemplateEngine templateEngine, String templateName, Map<String,Object> variables, HttpServletResponse response, String fileName ){
        // 断言参数不为空
        ResponseEnum.TEMPLATE_DATA_NULL.assertNotEmpty(variables);
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        // 设置编码、文件ContentType类型、文件头、下载文件名
        response.setCharacterEncoding("utf-8");
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
}
