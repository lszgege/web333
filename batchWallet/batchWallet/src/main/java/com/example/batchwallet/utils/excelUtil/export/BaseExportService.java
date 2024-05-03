package com.example.batchwallet.utils.excelUtil.export;


import com.example.batchwallet.utils.batchWallet.WalletExcelExportDto;
import com.example.batchwallet.utils.excelUtil.ExcelBusTyoe;

import java.util.*;


/**
 * Excel数据导出实现基类
 *
 * @Author: lszgege
 * @Date: 2023/5/30 18:47
 */

public abstract class BaseExportService<T extends BaseExportVo> {


    public abstract ExcelBusTyoe getType();


    public abstract List<WalletExcelExportDto> getData(Map<String, Object> paramMap);


    public abstract Class<WalletExcelExportDto> getClassName();


    public abstract String getOutputFileName(Map<String, Object> paramMap);

}
