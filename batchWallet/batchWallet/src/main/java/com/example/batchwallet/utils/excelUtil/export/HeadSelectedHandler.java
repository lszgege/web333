package com.example.batchwallet.utils.excelUtil.export;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Objects;

/**
 * @Author: lszgege
 * @Date: 2023/5/31 14:44
 */
public class HeadSelectedHandler implements SheetWriteHandler {

    /**
     * 下拉框值
     */
    private Map<Integer, String[]> dropDownMap;

    /**
     * 多少行有下拉
     */
    private final static Integer rowSize = 10000;

    public HeadSelectedHandler(Map<Integer, String[]> dropDownMap) {
        this.dropDownMap = dropDownMap;
    }

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        //XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        if (!CollectionUtils.isEmpty(dropDownMap)) {
            dropDownMap.forEach((celIndex, selectedValue) -> {
                if (Objects.nonNull(selectedValue) && selectedValue.length > 0) {
                    // 区间设置
                    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, rowSize, celIndex, celIndex);
                    // 下拉内容
                    XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(selectedValue);
                    XSSFDataValidation dataValidation = (XSSFDataValidation) dvHelper.createValidation(constraint, cellRangeAddressList);
                    dataValidation.setSuppressDropDownArrow(true);
                    dataValidation.setErrorStyle(XSSFDataValidation.ErrorStyle.STOP);
                    dataValidation.createErrorBox("提示", "请输入或者选择下拉框里的数据");
                    dataValidation.setShowErrorBox(true);
                    dataValidation.setShowPromptBox(true);
                    sheet.addValidationData(dataValidation);
                }
            });
        }
    }
}
