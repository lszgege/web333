package com.example.batchwallet.api;

import com.example.batchwallet.utils.excelUtil.export.BaseExportService;
import com.example.batchwallet.utils.excelUtil.export.ExcelExceute;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author lszgege
 * @Date 2024/5/3 22:28
 **/


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/web333")
@Validated
public class BatchWalletController {

    private final ExcelExceute excelExceute;

    /**
     * 导出
     *
     * @param businessType 业务类型
     * @param paramMap     参数Map
     */
    @PostMapping("/create/batch/wallet/{businessType}")
    public void export(@PathVariable("businessType") String businessType,
                       @RequestBody(required = false) Map<String, Object> paramMap,
                       HttpServletResponse response) {
        excelExceute.exportExcel(paramMap, response, businessType);
    }

}
