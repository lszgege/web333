package com.example.batchwallet.utils.batchWallet;

import java.math.BigInteger;
import java.security.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

/**
 * @Author lszgege
 * @Date 2024/5/3 21:51
 * 批量生成以太坊钱包并导出到Excel中
 **/
public class BatchWalletUtil {

    public static List<WalletExcelExportDto> batchCreateWallet(Integer walletNumber) {
        List<WalletExcelExportDto> walletExcelExportDtos = new ArrayList<>();
        for (int i = 1; i <= walletNumber; i++) {
            // 生成随机的私钥
            SecureRandom secureRandom = new SecureRandom();
            byte[] privateKeyBytes = new byte[32];
            secureRandom.nextBytes(privateKeyBytes);
            String privateKey = Numeric.toHexStringNoPrefix(privateKeyBytes);
            // 通过私钥生成公钥和地址
            ECKeyPair keyPair = ECKeyPair.create(new BigInteger(privateKey, 16));
            String publicKey = keyPair.getPublicKey().toString(16);
            String address = Keys.getAddress(keyPair.getPublicKey());
            walletExcelExportDtos.add(WalletExcelExportDto.builder()
                    .address(address)
                    .privateKey(privateKey)
                    .publicKey(publicKey)
                    .createDate(LocalDateTime.now())
                    .build());
        }
        return walletExcelExportDtos;
    }




}
