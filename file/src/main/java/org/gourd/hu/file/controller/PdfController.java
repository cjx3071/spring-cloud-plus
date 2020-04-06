package org.gourd.hu.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.file.utils.PdfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pdf预览、下载
 *
 * @author gourd.hu
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/pdf")
@Api(tags = "pdf", description = "pdf功能")
public class PdfController {

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * pdf预览
     *
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/preview")
    @ApiOperation(value="pdf预览")
    public void preview(HttpServletResponse response) {
        // 构造freemarker模板引擎参数,listVars.size()个数对应pdf页数
        List<Map<String,Object>> listVars = new ArrayList<>();
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试预览PDF!");
        Map<String,Object> variables2 = new HashMap<>(4);
        variables2.put("title","测试预览PDF2!");
        listVars.add(variables);
        listVars.add(variables2);

        PdfUtil.preview(templateEngine,"pdfPage",listVars,response);
    }

    /**
     * pdf下载
     *
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/download")
    @ApiOperation(value="pdf下载")
    public void download(HttpServletResponse response) {
        List<Map<String,Object>> listVars = new ArrayList<>();
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试下载PDF!");
        listVars.add(variables);
        PdfUtil.download(templateEngine,"pdfPage",listVars,response,"测试打印.pdf");
    }
}