package org.gourd.hu.doc.excel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.Arrays;
import java.util.List;

/**
 * 用户自定义拦截器
 *
 * @author gourd.hu
 */
@Slf4j
public class UserSheetWriteHandler implements SheetWriteHandler {


    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        log.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());
        // 设置下拉
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 100, 2, 2);
        // 默认下拉（下拉数据不能超过 255个字符，否则显示为空）
        addValidation(writeSheetHolder, cellRangeAddressList,new String[] {"男", "女"});
        // 隐藏sheet选择下拉，无限制(一般常见于数据库查询出的未知数量的数据)
        CellRangeAddressList cellSheetRangeAddressList = new CellRangeAddressList(1, 100, 1, 1);
        String hiddenName = setHidden(writeWorkbookHolder, Arrays.asList("18", "19","20", "21","22", "23","24", "25","26", "27","28", "29",
                "30", "31","32", "33","34", "35","36", "37","38", "39","40", "41","42", "43","44", "45","46", "47", "48","49", "50"));
        addValidation(writeSheetHolder, cellSheetRangeAddressList, hiddenName);
    }

    private String setHidden(WriteWorkbookHolder writeWorkbookHolder, List<String> collect) {
        // 解决下拉数据过多问题
        // 获取一个workbook
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        // 创建一个名称为 hidden 的sheet
        String hiddenName = "hidden";
        Sheet hidden = workbook.createSheet(hiddenName);
        // 隐藏sheet
        workbook.setSheetHidden(1,true);
        Name category1Name = workbook.createName();
        category1Name.setNameName(hiddenName);
        // 循环赋值（为了防止下拉框的行数与隐藏域的行数相对应，将隐藏域加到结束行之后）
        for (int i = 0, length = collect.size(); i < length; i++) {
            // 表示你开始的行数  3表示 你开始的列数
            hidden.createRow(i).createCell(0).setCellValue(collect.get(i));
        }
        // A1:A代表隐藏域创建第N列createCell(N)时。以A1列开始A行数据获取下拉数组
        category1Name.setRefersToFormula(hiddenName + "!$A$1:$A$" + (collect.size()));
        return hiddenName;
    }


    private void addValidation(WriteSheetHolder writeSheetHolder, CellRangeAddressList cellRangeEirTradeList, String[] labels) {
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(labels);
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeEirTradeList);
        writeSheetHolder.getSheet().addValidationData(dataValidation);
    }

    private void addValidation(WriteSheetHolder writeSheetHolder, CellRangeAddressList cellRangeEirTradeList, String hiddenName) {
        DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
        //5 将刚才设置的sheet引用到你的下拉列表中
        DataValidationConstraint constraint = helper.createFormulaListConstraint(hiddenName);
        DataValidation dataValidation = helper.createValidation(constraint, cellRangeEirTradeList);
        writeSheetHolder.getSheet().addValidationData(dataValidation);
    }
}