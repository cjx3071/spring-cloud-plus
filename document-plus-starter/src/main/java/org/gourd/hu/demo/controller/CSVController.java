package org.gourd.hu.demo.controller;

import com.lowagie.text.xml.xmp.XmpWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.doc.utils.CSVUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV测试接口类
 * @author gourd.hu
 */
@Slf4j
@RestController
@RequestMapping(value = "/csv")
@Api(tags = "CSV上传、下载")
public class CSVController {

    /**
     * @param response
     * @return
     * @Description 下载CSV
     **/
    @GetMapping("/download")
    @ApiOperation(value="下载CSV")
    public void downloadAllUserRoleCSV(HttpServletResponse response) {
        String[] head = new String[]{"姓名","年龄","手机号"};
        List<String[]> values = new ArrayList(){{
            add(new String[]{"弗瑞夫","108","110108"});
            add(new String[]{"葫芦胡","28","101101"});
        }};
        String fileName = "测试CSV.csv";
        File file = null;
        try {
            file = CSVUtils.makeTempCSV(fileName, head, values);
        } catch (IOException e) {
            log.error("创建CSV文件失败：",e);
        }
        response.setCharacterEncoding(XmpWriter.UTF8);
        response.setContentType("multipart/form-data");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileName.getBytes("gb2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage(), e);
        }
        CSVUtils.downloadFile(response, file);
    }

    /**
     * @return
     * @Description 上传CSV
     * @Param file
     **/
    @PostMapping(value = "/upload")
    @ApiOperation(value="上传CSV")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
        	//上传内容不能为空
            ResponseEnum.FILE_NOT_EXIST.assertNotNull(multipartFile);
            List<List<String>> stringMap = CSVUtils.readCSV(multipartFile, 3);

            return BaseResponse.ok("上传CSV成功");
        } catch (Exception e) {
            log.error("上传CSV失败：",e);
        }
        return BaseResponse.ok("上传CSV失败");
    }
}