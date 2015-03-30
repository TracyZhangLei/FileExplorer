package com.tracy.fileexplorer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;


/**
 * 时间工具类
 * @author tracyZhang
 *
 */
public class TimeUtils {
	
	public static SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
	public static SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat format2 = new SimpleDateFormat("HH:mm");
	
	public static String getTimeDiffStr(long time) {
		StringBuilder result = new StringBuilder();
		long diff = System.currentTimeMillis() - time;
		long MIN = 60*1000;
		long HOUR = 60*MIN;
		long DAY = 24*HOUR;
		long MONTH = 30*DAY;
		try{
			if(diff<0) {
				result.append(format.format(new Date(time)));
			}else if(diff<MIN) {//1分钟之前
				result.append("刚刚");
			}else if(diff<HOUR) {//1小时之前
				result.append(diff/MIN).append("分钟前");
			}else if(diff<DAY) {//1天之前
				result.append(diff/HOUR).append("小时前");
			}else if(diff<MONTH) {//1月之前
				result.append(diff/DAY).append("天前");
			}else {//很久之前
				result.append(format.format(new Date(time)));
			}
		}catch(Exception e){
			Log.e("TimeUtils", e.toString());
			return "";
		}
		return result.toString();
	}
	
	public static String getDateTime(long time){
		return format1.format(new Date(time));
	}
	
	
	/**
	 * 同服务器时间差在5分钟内不显示时间，一小时内小时HH:mm 其他显示MM-dd HH:mm
	 * @param time
	 * @return
	 */
	public static String getSmsTime(long time){
		StringBuilder result = new StringBuilder();
		long diff = Math.abs(System.currentTimeMillis() - time);
		long MIN = 60*1000;
		long HOUR = 60*MIN;
		long DAY = 24*HOUR;
		try{
			if(diff<MIN*5) {//5分钟之前
			}else if(diff<DAY) {//1天之前
				result.append(format2.format(new Date(time)));
			}else {//很久之前
				result.append(format.format(new Date(time)));
			}
		}catch(Exception e){
			Log.e("TimeUtils", e.toString());
			return "";
		}
		return result.toString();
	}

}
