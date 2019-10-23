package com.foo.faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import com.foo.dao.Jdbc;
import com.foo.util.MD5;
import com.github.javafaker.Faker;

public class replyFaker {
	public static void main(String[] args) {
		replyFaker();
	}

	public static void replyFaker() {
		Faker faker = Faker.instance(Locale.CHINA);
		for (int i = 101; i <= 200; i++) {
			String replyContent = faker.lorem().paragraph();
			Random r = new Random();
			int uid = r.nextInt(100) + 1;
			int commentId = r.nextInt(100) + 1;
			Date dates = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
			// postTime
			String date = dateFormat.format(dates);
			// content
			String content = faker.lorem().paragraph();
			int rid = r.nextInt(200 - 101 + 1) + 101;

			String sql = "insert into reply(uid,commentId,replyTime,content,`to`) values(?,?,?,?,?)";
			try {
				Jdbc.update(sql, uid, commentId, date, content, rid);
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}
}