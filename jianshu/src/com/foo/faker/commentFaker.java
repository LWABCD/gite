package com.foo.faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.foo.dao.Jdbc;

import com.foo.util.randomdate;
import com.github.javafaker.Faker;
import com.mysql.jdbc.RandomBalanceStrategy;

public class commentFaker {
	public static void main(String[] args) {
		
	}
	public static void commentFaker() {
		Faker faker = Faker.instance(Locale.CHINA);
		for (int i = 0; i < 100; i++) {
			String content = faker.lorem().paragraph();
			Random r = new Random();
			int uid = r.nextInt(100) + 1;
			int articleId = r.nextInt(100) + 1;
			int zan = r.nextInt(100) + 1;
			String dateString = randomdate.randomDate("2018-01-01", "2019-06-31");
			String sql = "insert into comment(uid,commentTime,content,zan,floor) values(?,?,?,?,?)";
			try {
				Jdbc.update(sql, uid, dateString, content, zan, 0);
			} catch (Exception e) {

			}
		}
	}
}