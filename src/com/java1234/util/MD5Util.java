package com.java1234.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

/**
 * md5加密类
 * @author Administrator
 *
 */
public class MD5Util {
	public static String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest md5 = MessageDigest.getInstance("md5");
		BASE64Encoder base64en = new BASE64Encoder();//base64编码格式
		return base64en.encode(md5.digest(str.getBytes("utf-8")));
		
	}
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(EncoderPwdByMd5("123"));
	}

}
