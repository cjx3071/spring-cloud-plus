package org.gourd.hu.doc.utils;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.xml.xmp.XmpWriter;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * pdf处理工具类
 *
 * @author gourd.hu
 * @version 1.0
 */
@Slf4j
public class PdfUtil {

    /**
     * 以文件流形式下载到浏览器
     *
     * @param templateEngine   配置
     * @param templateName 模板名称
     * @param listVars     模板参数集
     * @param response     HttpServletResponse
     * @param fileName     下载文件名称
     */
    public static void download(TemplateEngine templateEngine, String templateName, List<Map<String, Object>> listVars, HttpServletResponse response, String fileName) {
        // 设置编码、文件ContentType类型、文件头、下载文件名
        response.setCharacterEncoding(XmpWriter.UTF8);
        response.setContentType("application/pdf");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        try (ServletOutputStream out = response.getOutputStream()) {
            generateAll(templateEngine, templateName, out, listVars);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * pdf下载到特定位置
     *
     * @param templateEngine   配置
     * @param templateName 模板名称
     * @param listVars     模板参数集
     * @param filePath     下载文件路径
     */
    public static void save(TemplateEngine templateEngine, String templateName, List<Map<String, Object>> listVars, String filePath) {
        try (OutputStream out = new FileOutputStream(filePath);) {
            generateAll(templateEngine, templateName, out, listVars);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * pdf预览
     *
     * @param templateEngine   配置
     * @param templateName 模板名称
     * @param listVars     模板参数集
     * @param response     HttpServletResponse
     */
    public static void preview(TemplateEngine templateEngine, String templateName, List<Map<String, Object>> listVars, HttpServletResponse response) {
        try (ServletOutputStream out = response.getOutputStream()) {
            generateAll(templateEngine, templateName, out, listVars);
            out.flush();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 按模板和参数生成html字符串,再转换为flying-saucer识别的Document
     *
     * @param templateName 模板名称
     * @param variables   模板参数
     * @return Document
     */
    private static Document generateDoc(TemplateEngine templateEngine, String templateName, Map<String, Object> variables)  {
        // 声明一个上下文对象，里面放入要存到模板里面的数据
        final Context context = new Context();
        context.setVariables(variables);
        StringWriter stringWriter = new StringWriter();
        try(BufferedWriter writer = new BufferedWriter(stringWriter)) {
            templateEngine.process(templateName,context, writer);
            writer.flush();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(stringWriter.toString().getBytes()));
        }catch (Exception e){
            ResponseEnum.TEMPLATE_PARSE_ERROR.assertFail(e);
        }
        return null;
    }

    /**
     * 核心: 根据Thymeleaf 模板生成pdf文档
     *
     * @param templateEngine 配置
     * @param templateName 模板名称
     * @param out          输出流
     * @param listVars     模板参数
     * @throws Exception 模板无法找到、模板语法错误、IO异常
     */
    private static void generateAll(TemplateEngine templateEngine, String templateName, OutputStream out, List<Map<String, Object>> listVars) throws Exception {
        // 断言参数不为空
        ResponseEnum.TEMPLATE_DATA_NULL.assertNotEmpty(listVars);
        ITextRenderer renderer = new ITextRenderer();
        //设置字符集(宋体),此处必须与模板中的<body style="font-family: SimSun">一致,区分大小写,不能写成汉字"宋体"
        ITextFontResolver fontResolver = renderer.getFontResolver();
        //避免中文为空设置系统字体
        fontResolver.addFont("static/fonts/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 如果linux下有问题，可使用以下方式解决。
        // 有一个项目是用docker部署的，一直报错找不到simsun.ttf文件，但需要将simsun.ttf上传到/usr/share/fonts
        //fontResolver.addFont(CommonUtil.isLinux() ? "/usr/share/fonts/simsun.ttf" : "static/fonts/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        //根据参数集个数循环调用模板,追加到同一个pdf文档中
        //(注意:此处从1开始,因为第0是创建pdf,从1往后则向pdf中追加内容)
        for (int i = 0; i < listVars.size(); i++) {
            Document docAppend = generateDoc(templateEngine, templateName, listVars.get(i));
            renderer.setDocument(docAppend, null);
            //展现和输出pdf
            renderer.layout();
            if(i==0){
                renderer.createPDF(out, false);
            }else {
                //写下一个pdf页面
                renderer.writeNextDocument();
            }

        }
        renderer.finishPDF(); //完成pdf写入
    }
}