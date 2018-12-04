package com.qingpu.phone.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @Desc   MD5和sha1加密工具类
 * @author Gangyahaidao
 */
public class EncryptUtils {
	
	public static String getNonceStr() {
		Random random = new Random();
		return EncryptUtils.encryptByMD5(String.valueOf(random.nextInt(10000)));
	}
	
	public static String getTimeStamp() {
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	/**
	 * @Desc 使用MD5加密
	 * */
	public static String encryptByMD5(String content){
		String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance("md5");
            m.update(content.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
	}
	
	/**
	 * @Desc 使用sha-1加密
	 * */
	public static String encryptBySha1(String content){
		String encryptText = null;
        try {
            MessageDigest m = MessageDigest.getInstance("sha-1");
            m.update(content.getBytes("UTF8"));
            byte s[] = m.digest();
            // m.digest(inputText.getBytes("UTF8"));
            return hex(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encryptText;
	}
	
	// 返回十六进制字符串
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i =  0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring( 1, 3));
        }
        return sb.toString();
    }
}
