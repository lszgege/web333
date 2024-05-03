package com.example.batchwallet.utils.excelUtil.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;

import com.example.batchwallet.utils.excelUtil.export.BaseImportVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: lszgege
 * @Date: 2023/5/30 18:49
 */
@Slf4j
@RequiredArgsConstructor
public abstract class BaseImportService<T extends BaseImportVo> {

    /**
     * 导入
     *
     * @param response
     */
    public void importExcel(MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        // 文件校验
        this.fileCheck(file);
        // 解析数据
        List<T> data = this.readExcelData(file);
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        // 数据校验
        Boolean dataCheckResult = this.dataCheck(data);
        if (!dataCheckResult) {
            this.reponseErrorExcel(data, response);
            return;
        }
        // 数据入库
        this.saveDate(data);
    }

    /**
     * 文件格式检查
     *
     * @param file
     */
    private void fileCheck(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName.indexOf(".") == -1 || !ExcelTypeEnum.XLSX.getValue().equals(fileName.substring(fileName.lastIndexOf(".")))) {

        }
    }

    /**
     * 解析数据
     *
     * @param file
     * @return
     */
    private List<T> readExcelData(MultipartFile file) {
        List<T> list = ListUtils.newArrayListWithExpectedSize(1000);
        try {
            EasyExcel.read(new ByteArrayInputStream(file.getBytes()))
                    .registerReadListener(new AnalysisEventListener<T>() {
                        @Override
                        public void invoke(T data, AnalysisContext context) {
                            list.add(data);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {
                        }
                    }).head(this.getObjectClass()).excelType(ExcelTypeEnum.XLSX).sheet().doRead();
        } catch (Exception e) {
            log.error("=====>解析Excel文件异常", e);

        }
        return list;
    }

    /**
     * 返回错误excel
     *
     * @param response
     */
    private void reponseErrorExcel(List<T> data, HttpServletResponse response) {
        try {
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode(this.getOutputFileName(), "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream())
                    .registerConverter(new LongStringConverter())
                    .head(this.getObjectClass())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(0)
                    .doWrite(data);
        } catch (IOException e) {
            log.error("=====>返回错误Excel文件异常", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取导入实体Class
     *
     * @return
     */
    protected abstract Class<T> getObjectClass();

    /**
     * 获取返回的错误文件名称
     *
     * @return
     */
    protected abstract String getOutputFileName();

    /**
     * 数据校验
     *
     * @return
     */
    protected abstract Boolean dataCheck(List<T> data);

    /**
     * 数据入库
     *
     * @return
     */
    public abstract void saveDate(List<T> data);
}
