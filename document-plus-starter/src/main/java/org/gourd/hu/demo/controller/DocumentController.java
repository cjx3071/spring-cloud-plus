package org.gourd.hu.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.doc.openoffice.utils.CommonUtil;
import org.gourd.hu.doc.utils.ImageUtil;
import org.gourd.hu.doc.utils.PdfUtil;
import org.gourd.hu.doc.utils.WordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
 * 文档预览、下载
 *
 * @author gourd.hu
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/document")
@Api(tags = "文档预览、下载API")
public class DocumentController {

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${document.windowsFileTempLoc}")
    private String pdfWindowsPath;

    @Value("${document.linuxFileTempLoc}")
    private String pdfLinuxPath;

    @Value("${server.ssl.enabled}")
    private Boolean sslEnabled;
    /**
     * pdf预览
     *
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/pdf/preview")
    @ApiOperation(value="pdf预览")
    public void preview(HttpServletResponse response) {
        // 构造freemarker模板引擎参数,listVars.size()个数对应pdf页数
        List<Map<String,Object>> listVars = new ArrayList<>();
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试预览PDF!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
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
    @GetMapping(value = "/pdf/download")
    @ApiOperation(value="pdf下载")
    public BaseResponse<String> download(HttpServletResponse response) {
        List<Map<String,Object>> listVars = new ArrayList<>(1);
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试下载PDF!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        listVars.add(variables);
        PdfUtil.download(templateEngine,"pdfPage",listVars,response,"测试打印.pdf");
        return BaseResponse.ok("pdf下载成功");
    }

    /**
     * pdf下载到特定位置
     *
     */
    @GetMapping(value = "/pdf/save")
    @ApiOperation(value="pdf下载到特定位置")
    public BaseResponse<String> pdfSave() {
        List<Map<String,Object>> listVars = new ArrayList<>(1);
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试下载PDF!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        listVars.add(variables);
        // pdf文件下载位置
        String pdfPath = CommonUtil.isLinux() ? pdfLinuxPath : pdfWindowsPath + "test.pdf";
        PdfUtil.save(templateEngine,"pdfPage",listVars,pdfPath);
        return BaseResponse.ok("pdf保存成功");
    }

    /**
     * word下载
     *
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/word/download")
    @ApiOperation(value="word下载")
    public BaseResponse<String> downloadWord(HttpServletResponse response) {
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试预览Word!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        WordUtil.download(templateEngine,"pdfPage",variables,response,"测试打印.docx");
        return BaseResponse.ok("word下载成功");
    }

    /**
     * word预览
     *
     * @param response HttpServletResponse
     */
    @GetMapping(value = "/word/preview")
    @ApiOperation(value="word预览")
    public void previewWord(HttpServletResponse response) {
        // 构造freemarker模板引擎参数,listVars.size()个数对应pdf页数
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试预览Word!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        WordUtil.preview(templateEngine,"pdfPage",variables,response);
    }

    /**
     * word下载到特定位置
     *
     */
    @GetMapping(value = "/word/save")
    @ApiOperation(value="word下载到特定位置")
    public BaseResponse<String> wordSave() {
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","测试预览Word!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        // pdf文件下载位置
        String pdfPath = CommonUtil.isLinux() ? pdfLinuxPath : pdfWindowsPath +  "test.docx";
        WordUtil.save(templateEngine,"pdfPage",variables,pdfPath);
        return BaseResponse.ok("word保存成功");
    }

    /**
     * image下载到特定位置
     *
     */
    @GetMapping(value = "/image/save")
    @ApiOperation(value="image下载到特定位置")
    public BaseResponse<String> imageSave() {
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","image下载到特定位置!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        // pdf文件下载位置
        String pdfPath = CommonUtil.isLinux() ? pdfLinuxPath : pdfWindowsPath +  "test0.png";
        ImageUtil.saveAsImage(templateEngine,"pdfPage",variables,pdfPath);
        return BaseResponse.ok("image保存成功");
    }

    /**
     * image浏览器下载
     *
     */
    @GetMapping(value = "/image/download")
    @ApiOperation(value="image浏览器下载")
    public BaseResponse<String> imageDownload(HttpServletResponse response) {
        Map<String,Object> variables = new HashMap<>(4);
        variables.put("title","image浏览器下载!");
        variables.put("imageUrl",sslEnabled?"https://localhost:10001/imgs/sg.jpg":"http://localhost:10001/imgs/sg.jpg");
        List<Map<String,String>> demoList = new ArrayList<>();
        Map<String,String> demoMap = new HashMap<>(8);
        demoMap.put("name","哈哈");
        demoMap.put("nick","娃娃");
        demoMap.put("age","19");
        Map<String,String> demoMap2 = new HashMap<>(8);
        demoMap2.put("name","天天");
        demoMap2.put("nick","饭饭");
        demoMap2.put("age","14");
        demoList.add(demoMap);
        demoList.add(demoMap2);
        variables.put("demoList",demoList);
        ImageUtil.download(templateEngine,"pdfPage",variables,response,"test.jpg");
        return BaseResponse.ok("image保存成功");
    }
}