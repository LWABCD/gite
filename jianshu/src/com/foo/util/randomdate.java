package com.foo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * �����������
 * @author leibin
 *
 */
public class randomdate {
	public static void main(String[] args) {
		//sysTime();
	}
	//���ø�ʽ
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
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
    //��ȡϵͳ��ǰʱ��
	public static String sysTime(){
        try {
        	Date date=new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");//���Է�����޸����ڸ�ʽ
            String dateString=dateFormat.format(date);
            System.out.println(dateString);
            return dateString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
