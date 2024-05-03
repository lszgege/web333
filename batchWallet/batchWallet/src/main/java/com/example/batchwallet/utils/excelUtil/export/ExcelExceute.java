package com.example.batchwallet.utils.excelUtil.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.example.batchwallet.utils.excelUtil.ExcelBusTyoe;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @Author lszgege
 * @Date 2024/5/3 22:48
 **/

@Slf4j
@Service
@Data
public class ExcelExceute {

    private final List<BaseExportService> baseExportServices;

    /**
     * 导出
     *
     * @param paramMap
     * @param response
     */
    public <T extends BaseExportVo> void exportExcel(Map<String, Object> paramMap, HttpServletResponse response, String type) {
        try {
            ExcelBusTyoe excelBusTyoe = checkBusType(type);
            BaseExportService service = baseExportServices.stream()
                    .filter(s -> s.getType().equals(excelBusTyoe))
                    .findFirst()
                    .orElse(null);
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(service.getOutputFileName(paramMap), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<T> data = service.getData(paramMap);
            EasyExcel.write(response.getOutputStream())
                    .registerConverter(new LongStringConverter())
                    .registerWriteHandler(this.getSheetWriteHandler())
                    .head(service.getClassName())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(this.getSheetName())
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ExcelBusTyoe checkBusType(String typeStr) {
        ExcelBusTyoe type = ExcelBusTyoe.valueOf(typeStr);
        if (Objects.isNull(type)) {
            log.warn("流程不支持该业务");
            throw new RuntimeException("流程不支持该业务");
        }
        return type;
    }

    /**
     * 导出模板（无数据）
     *
     * @param paramMap
     * @param response
     */
    public <T extends BaseExportVo> void exportExcelTemplate(Map<String, Object> paramMap, HttpServletResponse response, String type) {
        try {
            ExcelBusTyoe excelBusTyoe = checkBusType(type);
            BaseExportService service = baseExportServices.stream()
                    .filter(s -> s.getType().equals(excelBusTyoe))
                    .findFirst()
                    .orElse(null);
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(service.getOutputFileName(paramMap), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            List<T> data = service.getData(paramMap);
            EasyExcel.write(response.getOutputStream())
                    .registerConverter(new LongStringConverter())
                    .registerWriteHandler(this.getSheetWriteHandler())
                    .head(service.getClassName())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(this.getSheetName())
                    .doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取Sheet名称
     *
     * @return
     */
    protected String getSheetName() {
        return "Sheet1";
    }

    /**
     * 设置excel下拉框
     *
     * @return
     */
    protected SheetWriteHandler getSheetWriteHandler() {
        return new HeadSelectedHandler(new HashMap<>());
    }

}
