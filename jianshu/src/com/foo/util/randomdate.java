package com.foo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 生成随机日期
 * @author leibin
 *
 */
public class randomdate {
	public static void main(String[] args) {
		//sysTime();
	}
	//调用格式
	//String dateString=randomdate.randomDate("2018-01-01","2019-06-31");
	public static String randomDate(String beginDate,String endDate){
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date start = format.parse(beginDate);
            Date end = format.parse(endDate);
 
            if(start.getTime() >= end.getTime()){
                return null;
            }
            long date = random(start.getTime(),end.getTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
            String dateString=dateFormat.format(date);
        
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    private static long random(long begin,long end){
        long rtn = begin + (long)(Math.random() * (end - begin));
        if(rtn == begin || rtn == end){
            return random(begin,end);
        }
        return rtn;
    }
    //获取系统当前时间
	public static String sysTime(){
        try {
        	Date date=new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");//可以方便地修改日期格式
            String dateString=dateFormat.format(date);
            System.out.println(dateString);
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
