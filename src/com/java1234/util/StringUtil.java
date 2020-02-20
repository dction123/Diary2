package com.java1234.util;
/**
 *格式化字符串类
 * @author Administrator
 *
 */
public class StringUtil {
	
	
	/**
	 * 判断是否是空串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if("".equals(str) || str == null){
			return true;
		}else{
			return false;
		}
		
	}
	
	
	public static boolean isNotEmpty(String str){
		if(!"".equals(str) && str != null ){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		//System.out.println(isEmpty("22"));
		System.out.println(isNotEmpty(""));
	}
	

}
