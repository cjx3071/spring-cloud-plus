package org.gourd.hu.demo.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.gourd.hu.doc.openoffice.utils.CommonUtil;
import org.gourd.hu.doc.openoffice.utils.FileConvertUtil;
import org.gourd.hu.doc.openoffice.utils.OfficeConvertToPdf;
import org.gourd.hu.doc.openoffice.utils.Pdf2htmlExUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件转pdf控制类
 * 单个转换
 *
 * @author Administrator
 * @date 2017/2/14
 */
@RestController
@RequestMapping("/openOffice")
@Api(tags = "openOffice测试API", description = "openOffice控制器")
@Slf4j
public class FileConvertController{

    /**
     * office文件转pdf
     * 单个转换
     * controller直接处理
     * @param request
     * @param response
     */
    @PostMapping("/office-to-pdf")
    @ApiOperation(value="office文件转pdf")
    public void officeConvertToPdf(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>(2);
        resultMap.put("success", false);
        //获取输出文件目标路径
        String outputPath = FileConvertUtil.getOutputPath() ;
        //spring的文件上传插件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile filedata = multipartRequest.getFile("file");
        if (filedata == null){
            return;
        }
       //获得上传文件的名称
        String fileName = filedata.getOriginalFilename();
        if(fileName == null){
            return;
        }
        //获得上传文件的后缀名
        String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (extensionName == null){
            return;
        }
        //将MultipartFile对象转换成File对象
        File file = null;
        try {
            file = File.createTempFile("prefix", "_" + filedata.getOriginalFilename());
            filedata.transferTo(file);
        } catch (IOException e) {
            log.error("MultipartFile对象转换成File对象异常：",e);
        }

        //判断文件格式
        if(CommonUtil.inTypes(extensionName)) {
            //调用转换工具类转换文件
            boolean flag = OfficeConvertToPdf.converterFiles(file, outputPath, fileName);
            //文件转换完成后 将其写到项目文件upload目录下
            if (flag) {
                //判断预览文件时候存在
                if (StringUtils.isNotEmpty(outputPath)) {
                    resultMap.put("success", true);
                    resultMap.put("outputName", outputPath);
                    try {
                        //设置response的输出字符集
                        response.setContentType("text/html;charset=UTF-8") ;
                        response.getWriter().write(JSON.toJSONString(resultMap));
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.print("数据发送异常，发送内容：" + JSON.toJSONString(resultMap)) ;
                    }
                }
            }
        }
    }
    /**
     * office文件转pdf
     * 通过文件夹路径遍历获得文件
     * 批量处理
     * @param request
     * @param response
     */
    @PostMapping("/folder-convert")
    @ApiOperation(value="office文件转pdf")
    @ResponseBody
    public void folderLisrConvert(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>(2);
        resultMap.put("success", false);
        String folderPath = request.getParameter("path") ;
        File[] files = FileConvertUtil.traverseFolder2(folderPath) ;
        for (File file : files){
            String fileName = file.getName() ;
            // 获得文件的后缀名
            String extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 获取输出文件目标路径
            String outputPath = FileConvertUtil.getOutputPath() ;
            // 判断上传文件的类型
            if(CommonUtil.inTypes(extensionName)){
                // 调用转换工具类转换文件
                boolean flag = OfficeConvertToPdf.converterFiles(file,outputPath,fileName);
                // 文件转换完成后 将其写到项目文件upload目录下
                if (flag) {
                    if(StringUtils.isNotEmpty(outputPath)) {
                        resultMap.put("success", true);
                        resultMap.put("outputPaths", outputPath);
                    }
                    try {
                        // 设置response的输出字符集
                        response.setContentType("text/html;charset=UTF-8") ;
                        response.getWriter().write(JSON.toJSONString(resultMap));
                    } catch (IOException e) {
                        log.error(e.getMessage(),e);
                        log.info("数据发送异常，发送内容：" + JSON.toJSONString(resultMap)); ;
                    }
                }
            }
        }
    }

    /**
     * 将pdf文件转换成html
     * @param pdfPath
     * @return
     */
    @PostMapping("/pdf-to-html")
    @ResponseBody
    public Map<String,Object> getHtmlName(@RequestParam(required = false,defaultValue = "") String pdfPath){
        Map<String,Object> resultMap = new HashMap<String,Object>(2) ;
        resultMap.put("success",false);
        boolean linux = CommonUtil.isLinux();
        if(!"".equals(pdfPath)){
            String htmlName = FileConvertUtil.getOutHtmlName() ;
            //将pdf转换成html文件
            Boolean flag = Pdf2htmlExUtil.pdf2html(linux?CommonUtil.LINUX_PDFHTMLEXPATH:CommonUtil.WINDOWS_PDFHTMLEXPATH,pdfPath,linux? CommonUtil.LINUX_TARGETPATH:CommonUtil.WINDOWS_TARGETPATH,htmlName);
            if(flag) {
                resultMap.put("success", true);
                resultMap.put("htmlString", FileConvertUtil.htmlToString(linux? CommonUtil.LINUX_TARGETPATH:CommonUtil.WINDOWS_TARGETPATH + "\\" + htmlName));
            }
        }
        return resultMap;
    }
}
