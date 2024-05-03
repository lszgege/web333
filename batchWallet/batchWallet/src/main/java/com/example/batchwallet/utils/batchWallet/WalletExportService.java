package com.example.batchwallet.utils.batchWallet;


import com.example.batchwallet.utils.excelUtil.ExcelBusTyoe;
import com.example.batchwallet.utils.excelUtil.export.BaseExportService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 钱包导出
 *
 * @author lszgege
 * @date 2023/5/30 16:46
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletExportService extends BaseExportService<WalletExcelExportDto> {


    @Override
    public ExcelBusTyoe getType() {
        return ExcelBusTyoe.WALLET;
    }

    @Override
    public List<WalletExcelExportDto> getData(Map<String, Object> paramMap) {
        int walletNumber = Integer.parseInt(paramMap.get("walletNumber").toString());
        return BatchWalletUtil.batchCreateWallet(walletNumber);
    }

    @Override
    public Class<WalletExcelExportDto> getClassName() {
        return WalletExcelExportDto.class;
    }

    @Override
    public String getOutputFileName(Map<String, Object> paramMap) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return "EVM链钱包生成" + date;
    }
}
