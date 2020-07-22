package org.gourd.hu.doc.openoffice.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.doc.openoffice.service.FileConvertService;
import org.gourd.hu.doc.openoffice.utils.CommonUtil;
import org.gourd.hu.doc.openoffice.utils.OfficeConvertToPdf;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/15.
 */
@Service
@Slf4j
public class FlieConvertServiceImpl implements FileConvertService {
    @Override
    public void officeConvertToPdf(File[] files,String outputPath, HttpServletResponse response) throws Exception {
        Map<String,Object> resultMap = new HashMap<String,Object>() ;
        String fileName = null ;
        String extensionName = null ;
        boolean flag = false ;
        String pathStrings = null ;
        if(files != null){
            for(File file : files){
                // 获得文件名
                fileName = file.getName() ;
                // 获得文件后缀名
                extensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
                if(CommonUtil.inTypes(extensionName)){
                    // 调用转换工具类转换文件
                    flag = OfficeConvertToPdf.converterFiles(file,outputPath,fileName);
                    if(flag){
                    }
                }else{

                }
            }
        }


        File fileOutPath = new File(outputPath);
        //判断文件时候存在 如果存在则转换成功 反之失败
        if (fileOutPath.exists()) {
            resultMap.put("success", true);
            resultMap.put("newFileName",fileOutPath);
        } else {
            resultMap.put("success", false);
        }
        //设置response的输出字符集
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(resultMap));
        } catch (IOException e) {
            e.printStackTrace();
            log.info("数据发送异常，发送内容：" + JSON.toJSONString(resultMap));
        }
    }
}
