package org.gourd.hu.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Description word处理工具类
 * @Author gourd.hu
 * @Date 2020/5/25 9:42
 * @Version 1.0
 */
@Slf4j
public class WordUtil {


    public static void generateDoc(TemplateEngine templateEngine, String templateName,PrintWriter responseWriter, Map<String,Object> variables) throws UnsupportedEncodingException {
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        try(BufferedWriter writer = new BufferedWriter(responseWriter)) {
            templateEngine.process(templateName,context, writer);
        }catch (Exception e){
            ResponseEnum.TEMPLATE_PARSE_ERROR.assertFail(e);
        }

    }
    /**
     * word下载
     *
     * @param templateEngine   配置
     * @param templateName 模板名称
     * @param variables     模板参数集
     * @param response     HttpServletResponse
     * @param fileName     下载文件名称
     */
    public static void download(TemplateEngine templateEngine, String templateName, Map<String, Object> variables, HttpServletResponse response, String fileName) {
        // 断言参数不为空
        ResponseEnum.TEMPLATE_DATA_NULL.assertNotEmpty(variables);
        // 设置编码、文件ContentType类型、文件头、下载文件名
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        try (PrintWriter responseWriter = response.getWriter()) {
            generateDoc(templateEngine, templateName, responseWriter, variables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
    /**
     * word预览
     *
     * @param templateEngine   配置
     * @param templateName 模板名称
     * @param variables     模板参数集
     * @param response     HttpServletResponse
     */
    public static void preview(TemplateEngine templateEngine, String templateName, Map<String, Object> variables, HttpServletResponse response) {
        // 断言参数不为空
        ResponseEnum.TEMPLATE_DATA_NULL.assertNotEmpty(variables);
        // 设置编码、文件ContentType类型、文件头、下载文件名
        response.setCharacterEncoding("gb2312");
        try (PrintWriter responseWriter = response.getWriter()) {
            generateDoc(templateEngine, templateName, responseWriter, variables);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
