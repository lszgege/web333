package com.example.batchwallet.utils.batchWallet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.example.batchwallet.utils.excelUtil.export.BaseExportVo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author lszgege
 * @Date 2024/5/3 22:02
 * 钱包excel导出对象
 **/

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class WalletExcelExportDto extends BaseExportVo {

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("私钥")
    private String privateKey;

    @ExcelProperty("公钥")
    private String publicKey;

    @ExcelProperty("生成日期")
    private LocalDateTime createDate;

}
