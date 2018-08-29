package com.dolo.wechat.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
* 描述: 校验消息是否来自微信
* 作者: dolojia
* 修改日期: 2018/8/29 下午9:39
* E-mail: dolojia@gmail.com
**/
public class SignUtil
{

    public static boolean checkSignature(String token, String signature, String timestamp, String nonce) throws NoSuchAlgorithmException
    {
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);
        StringBuffer content = new StringBuffer();
        for (int i = 0; i < arr.length; i++)
        {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(content.toString().getBytes());
        tmpStr = byteToStr(digest);
        content = null;
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }


    /**
     * 
     * 功能描述：将字节数组转换为十六进制字符串
     * 
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray)
    {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++)
        {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }


    /**
     * 将字节转换为十六进制字符串
     * 
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte)
    {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
