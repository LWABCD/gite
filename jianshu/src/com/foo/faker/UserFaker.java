package com.foo.faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.foo.dao.Jdbc;
import com.foo.util.MD5;
import com.foo.util.*;
import com.github.javafaker.Faker;

public class UserFaker {

	public static void main(String[] args) {
      userfaker();
	}
	public static void userfaker() {
	//Faker faker = new Faker(Locale.CHINA);
		
		Faker faker = Faker.instance(Locale.CHINA);
	
		for (int i =1; i <=200; i++) {
			// êÇ³Æ
			String nickName = faker.funnyName().name();
			// phone
			String phoen = faker.phoneNumber().cellPhone();
			// pwd
			String pwd = faker.regexify("[a-z0-9]{6,10}");
			// MD5 ¼ÓÃÜ
			pwd = MD5.encrypt(pwd);
			// info
			String info = faker.lorem().paragraph();
			String gender=faker.regexify("[ÄÐÅ®]{1}");
//			
			String dateString=randomdate.randomDate("2018-01-01","2019-06-31");
		   // String sql="update `user` set registerTime=? where uid=? ";
			String sql = "insert into `user`(nickName,phone,pwd,gender,info,redisterTime) values(?,?,?,?,?,?)";
			try {
				//Jdbc.update(sql, dateString,i);
				//System.out.println(gender+i);
				Jdbc.update(sql, nickName, phoen, pwd,gender,info,dateString);
			} catch(Exception e) {		
			}
		}
	}
}
