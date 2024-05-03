package com.example.batchwallet.utils.excelUtil.export;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 导出对象基类
 *
 * @Author: lszgege
 * @Date: 2023/5/30 22:54
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public class BaseExportVo implements Serializable {

    private static final long serialVersionUID = 1L;
}
