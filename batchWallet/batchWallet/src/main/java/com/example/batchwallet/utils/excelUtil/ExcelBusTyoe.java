package com.example.batchwallet.utils.excelUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author lszgege
 * @Date 2023/11/17 14:25
 * 适用对象类型
 **/
@Getter
@AllArgsConstructor
public enum ExcelBusTyoe {

    WALLET("钱包生成", 1),
    ;

    private String meaning;
    private int sortNum;


    public static ExcelBusTyoe of(String type) {
        if (null == type) {
            return null;
        }
        return Arrays.stream(values())
                .filter(e -> e.meaning.equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }


    public static boolean eq(String typeStr, ExcelBusTyoe type) {
        if (null == typeStr) {
            return false;
        }
        return type.name().equals(typeStr) ? true : false;
    }

}
