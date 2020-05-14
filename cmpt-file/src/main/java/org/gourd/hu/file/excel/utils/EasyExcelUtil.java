package org.gourd.hu.file.excel.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gourd.hu.base.common.response.BaseResponse;
import org.gourd.hu.base.holder.RequestHolder;
import org.gourd.hu.file.excel.entity.SheetExcelData;
import org.gourd.hu.file.excel.handler.CustomCellWriteHandler;
import org.gourd.hu.file.excel.listener.ExcelListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工具类
 *
 * @author liuyi
 * @Created 2019-7-18 18:01:53
 **/
@Slf4j
public class EasyExcelUtil {
    /**
     * 读取单个sheet的excel文件
     * @param excel 文件
     * @param t 实体类型
     * @param headRowNumber 头行数
     * @return
     * @throws Exception
     */
    public static <T> List<T> readSingleExcel(MultipartFile excel, T t,int headRowNumber) throws IOException {
        return EasyExcel.read(excel.getInputStream(), t.getClass(), new ExcelListener())
                .sheet().headRowNumber(headRowNumber).doReadSync();
    }

    /**
     * 读取多个sheet的excel文件
     * @param excel 文件
     * @param t 实体类型
     * @param headRowNumber 头行数
     * @return
     * @throws Exception
     */
    public static <T> List<T> readMultiExcel(MultipartFile excel, T t,int headRowNumber,int sheetNumber) throws IOException {
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
//        ExcelReader excelReader = EasyExcel.read(excel.getInputStream()).build();
//        ReadSheet readSheet =
//                EasyExcel.readSheet(sheetNumber).head(t.getClass()).headRowNumber(headRowNumber)
//                        .registerReadListener(new ExcelListener()).build();
//        excelReader.read(readSheet);
//        List<T> tList = EasyExcel.readSheet(sheetNumber).head(t.getClass()).headRowNumber(headRowNumber)
//                .registerReadListener(new ExcelListener()).doReadSync();
//        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
//        excelReader.finish();
        return new ArrayList<>();
    }

    /**
     * 导出文件
     * 导出模板时，tList传一个空list即可
     * @param tList 数据集
     * @param tClass 数据类型
     * @param <T>
     * @throws IOException
     */
    public static <T> void writeSingleExcel(String fileName,String sheetName, List<T> tList, Class tClass) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        try (ServletOutputStream outputStream = response.getOutputStream()){
            setResponse(fileName, response);
            EasyExcel.write(outputStream, tClass).autoCloseStream(Boolean.FALSE)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet(sheetName)
                    .doWrite(tList);
        } catch (Exception e) {
            errorWrite(response, e);
        }
    }

    /**
     * 导出多sheet
     * @param fileName 文件名
     * @param sheetExcelDataList sheet对象
     * @throws IOException
     */
    public static void writeMultiExcel(String fileName, List<SheetExcelData> sheetExcelDataList) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        ServletOutputStream outputStream = response.getOutputStream();
        setResponse(fileName, response);
        ExcelWriter excelWriter = EasyExcel.write(outputStream).autoCloseStream(false).build();
        try {
            for (int i = 0,length = sheetExcelDataList.size(); i < length; i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet(i+1, sheetExcelDataList.get(i).getSheetName())
                        .head(sheetExcelDataList.get(i).getTClass())
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).build();
                excelWriter.write(sheetExcelDataList.get(i).getDataList(), writeSheet);
            }
        } catch (Exception e) {
            errorWrite(response, e);
        }finally {
            // 刷新流，不加这句话，下载文件损坏打不开
            outputStream.flush();
//            outputStream.close();
            if(excelWriter != null){
                // 千万别忘记finish关闭流
                excelWriter.finish();
            }
        }
    }

    /**
     * 无对象导出
     * @param fileName
     * @param headList
     * @param dataList
     * @throws IOException
     */
    public static void writeWithoutModel(String fileName, List<List<String>> headList,List<List<Object>> dataList) throws IOException{
        HttpServletResponse response = RequestHolder.getResponse();
        try (ServletOutputStream outputStream = response.getOutputStream()){
            setResponse(fileName, response);
            // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
            EasyExcel.write(outputStream).head(headList).sheet("模板").registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(dataList);
        } catch (Exception e) {
            errorWrite(response, e);
        }
    }

    /**
     * 导出错误
     * @param response
     * @param e
     * @throws IOException
     */
    private static void errorWrite(HttpServletResponse response, Exception e) throws IOException {
        // 重置response
        response.reset();
        log.error(e.getMessage(), e);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().println(JSON.toJSONString(BaseResponse.fail("导出失败")));
    }

    /**
     * 设置导出信息
     * @param fileName
     * @param response
     * @throws UnsupportedEncodingException
     */
    private static void setResponse(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 重置response
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        fileName = URLEncoder.encode(fileName + DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss").format(LocalDateTime.now()) + ExcelTypeEnum.XLSX.getValue(), "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);

    }


}