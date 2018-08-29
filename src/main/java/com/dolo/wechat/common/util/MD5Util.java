package com.dolo.wechat.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MD5Util {

	private static final Logger LOGGER = LogManager.getLogger();
	
	 /**
     * @param data 明文
     * @return 密文大写
     */
    public static String md5(String data){
        return DigestUtils.md5Hex(data).toUpperCase();
    }

	
    /**
     * @param data 明文
     * @return 密文大写
     */
	public static String md5UpperCase(String data){
        return DigestUtils.md5Hex(data).toUpperCase();
    }
    
    /**
     * @param data 明文
     * @return 密文小写
     */
    public static String md5LowerCase(String data){
        return DigestUtils.md5Hex(data).toLowerCase();
    }

	public static String hex(byte[] array) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; ++i) {
			sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}

	public static String md5Hex(String message) {
		return md5Hex(message, "CP1252");
	}

	public static String md5Hex(String message, String encoding) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return hex(md.digest(message.getBytes(encoding)));
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Failed to generate md5 string", e);
			throw new RuntimeException("Failed to generate md5 string", e);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Failed to generate md5 string", e);
			throw new RuntimeException("Failed to generate md5 string", e);
		}
	}

	/**
	 * md5加密大写 返回hex String
	 * 
	 * @param source
	 * @return
	 */
	public static String md5HexUpperCase(String data, String encoding) {
		return md5Hex(data, encoding).toUpperCase();
	}

	public static void main(String[] args) {
		System.out.println(MD5Util.md5Hex("100089"));
	}

}