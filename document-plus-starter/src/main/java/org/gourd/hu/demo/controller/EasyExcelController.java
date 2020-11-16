package org.gourd.hu.demo.controller;

import com.alibaba.excel.exception.ExcelCommonException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.gourd.hu.base.exception.enums.ResponseEnum;
import org.gourd.hu.base.response.BaseResponse;
import org.gourd.hu.doc.excel.entity.DepartPO;
import org.gourd.hu.doc.excel.entity.SheetExcelData;
import org.gourd.hu.doc.excel.entity.UserPO;
import org.gourd.hu.doc.excel.enums.SexEnum;
import org.gourd.hu.doc.excel.handler.CustomCellStyleStrategy;
import org.gourd.hu.doc.excel.handler.UserSheetWriteHandler;
import org.gourd.hu.doc.excel.utils.EasyExcelUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description excel操作
 * @Author gourd
 * @Date 2019/12/30 15:41
 * @Version 1.0
 */
@Api(tags = "excel测试API", description = "excel操作" )
@RestController
@RequestMapping("/excel")
@Slf4j
public class EasyExcelController {


    /**
     * 单sheet文件导入
     *
     */
    @PostMapping("/single-read")
    @ResponseBody
    @ApiOperation(value = "单sheet文件导入")
    public BaseResponse singleImport(MultipartFile file) {
        // 校验文件类型
        String fileName = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel2003(fileName) && !EasyExcelUtil.isExcel2007(fileName)){
            ResponseEnum.EXCEL_TYPE_ERROR.assertFail();
        }
        // 校验模板
        EasyExcelUtil.checkModel(file);
        List<UserPO> userPOList = null;
        try {
            userPOList = EasyExcelUtil.readSingleExcel(file,new UserPO(),2);
        }catch (IOException e) {
            log.error("解析excel失败：",e);
            ResponseEnum.EXCEL_ANALYZE_FAIL.assertFail();
        } catch (ExcelCommonException e){
            log.error("文件类型错误：",e);
            ResponseEnum.EXCEL_TYPE_ERROR.assertFail();
        }
        if(CollectionUtils.isEmpty(userPOList)){
            ResponseEnum.EXCEL_NO_DATA.assertFail();
        }
        // 导入逻辑 .......
        return BaseResponse.ok("success");
    }

    /**
     * 多sheet文件导入
     *
     */
    @PostMapping("/multi-read")
    @ResponseBody
    @ApiOperation(value = "多sheet文件导入")
    public BaseResponse multiImport(MultipartFile file) throws IOException {
        List<UserPO> userPOList = EasyExcelUtil.readMultiExcel(file, new UserPO(),2, 1);
        return BaseResponse.ok();
    }


    /**
     * 单sheet文件导出
     *
     */
    @GetMapping("/single-export")
    @ResponseBody
    @ApiOperation(value = "单sheet文件导出")
    public void singleExport() throws IOException{
        List<UserPO> userPOList =new ArrayList<>(2);
        UserPO userPO = new UserPO();
        userPO.setAge(100);
        userPO.setName("gourd-hu");
        userPO.setSex(SexEnum.X);
        userPO.setBirth(LocalDateTime.now());
        userPO.setEmail("xxx.com");
        userPO.setMobilePhone("xxxx");
        userPOList.add(userPO);
        // 必填头部标红
        List<Integer> columnIndexes = Arrays.asList(0,1,2);
        List<Integer> rowIndexes = Arrays.asList(0,1);
        CustomCellStyleStrategy customCellStyleStrategy = new CustomCellStyleStrategy(rowIndexes,columnIndexes, IndexedColors.RED.getIndex());
        UserSheetWriteHandler customSheetWriteHandler = new UserSheetWriteHandler();
        EasyExcelUtil.writeSingleExcel("单sheet导出","用户",userPOList,UserPO.class,customCellStyleStrategy,customSheetWriteHandler);
    }



    /**
     * 多sheet文件导出
     *
     */
    @GetMapping("/multi-export")
    @ResponseBody
    @ApiOperation(value = "多sheet文件导出")
    public void multiSheetExport() throws IOException{
        List<UserPO> userPOList =new ArrayList<>(2);
        UserPO userPO = new UserPO();
        userPO.setAge(100);
        userPO.setName("gourd-hu");
        userPO.setSex(SexEnum.X);
        userPO.setBirth(LocalDateTime.now());
        userPO.setEmail("xxx.com");
        userPO.setMobilePhone("xxxx");
        userPOList.add(userPO);
        List<DepartPO> departPOList = new ArrayList<>();
        DepartPO departPO = new DepartPO();
        departPO.setName("技术部");
        departPO.setCode("001");
        departPO.setDescription("技术部");
        departPOList.add(departPO);
        List<SheetExcelData> sheetExcelDataList = new ArrayList<>(3);
        SheetExcelData<UserPO> userPOSheetExcelData = new SheetExcelData<>();
        userPOSheetExcelData.setSheetName("用户");
        userPOSheetExcelData.setTClass(UserPO.class);
        userPOSheetExcelData.setDataList(userPOList);
        SheetExcelData<DepartPO> departPOSheetExcelData = new SheetExcelData<>();
        departPOSheetExcelData.setSheetName("部门");
        departPOSheetExcelData.setTClass(DepartPO.class);
        departPOSheetExcelData.setDataList(departPOList);
        sheetExcelDataList.add(userPOSheetExcelData);
        sheetExcelDataList.add(departPOSheetExcelData);
        EasyExcelUtil.writeMultiExcel("多sheet导出",sheetExcelDataList);
    }

    /**
     * 无对象文件导出
     *
     */
    @GetMapping("/no-model-export")
    @ResponseBody
    @ApiOperation(value = "无对象文件导出")
    public void noModelExport() throws IOException{
        EasyExcelUtil.writeWithoutModel("测试无对象导出",head(),dataList());
    }


    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<>();
        List<String> head0 = new ArrayList<>();
        head0.add("统一头");
        head0.add("字符串");
        List<String> head1 = new ArrayList<>();
        head1.add("统一头");
        head1.add("数字" );
        List<String> head2 = new ArrayList<>();
        head2.add("统一头");
        head2.add("日期");
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<Object> data = new ArrayList<>();
            data.add("字符串" + i);
            data.add(0.56);
            data.add("2019-09-09 09:09:09");
            list.add(data);
        }
        return list;
    }

}
