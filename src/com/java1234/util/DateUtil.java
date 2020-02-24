package com.java1234.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化日期类
 * @author Administrator
 *
 */
public class DateUtil {
	/**
	 * 格式化日期到字符串 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date,String format){
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		if(date!=null){
			result = sdf.format(date);
			
		}
		return result;
		
	}
	
	/**
	 * 将字符串格式化为日期对象
	 * @param str
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date formatString(String str,String format) throws ParseException{
		
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.parse(str);
		
		
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentDateStr(){
		Date date = new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyMMddhhmmss");
		return sdf.format(date);
		
	}
	

}
