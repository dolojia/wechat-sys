package com.dolo.wechat.test.util;

import java.security.PublicKey;

import com.alibaba.druid.filter.config.ConfigTools;

public class DruidConfigTools {

    /**
     * 描述: druid密码加密<br>
     * 作者: dolojia
     * 修改日期: 2018/8/29 15:06
     * E-mail: dolojia@gmail.com
     **/
    public static void main(String[] args) throws Exception {
        String password = "xxxx";
        String[] arr = ConfigTools.genKeyPair(512);
        String privateKeyText = arr[0];
        String publicKeyText = arr[1];
        String encryptedPassword = ConfigTools.encrypt(arr[0], password);
        System.out.println("privateKey:" + privateKeyText);
        System.out.println("publicKey:" + publicKeyText);
        System.out.println("passwordKey:" + encryptedPassword);

        // 解密
        System.out.println("===========================================");
        PublicKey publicKey = ConfigTools.getPublicKey(publicKeyText);
        String passwordPlainText = ConfigTools.decrypt(publicKey, encryptedPassword);
        System.out.println("passwordPlainText:" + passwordPlainText);
    }

}
