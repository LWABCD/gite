package com.foo.faker;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.foo.dao.Jdbc;

public class ArticleFaker {

	public static void main(String[] args) {
		Articlefaker();
	}
	public static void Articlefaker() {


		Faker faker = Faker.instance(Locale.CHINA);

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			// articleid
			// int article=random.nextInt(100);

			// author
			int author = random.nextInt(100);

			// title
			String title = faker.artist().name();

			// content
			String content = faker.lorem().paragraph();

			// totalDiamond
			float totalDiamond = random.nextInt(4000);
			Date dates = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// 可以方便地修改日期格式
			// postTime
			String date = dateFormat.format(dates);

			// wordNum
			int wordNum = random.nextInt(3000);

			// readNum
			int readNum = random.nextInt(100);

			// commentNum
			int commentNum = random.nextInt(3000);

			// likeNum
			int likeNum = random.nextInt(100);

			String sql = "insert into article(author,title,content,	"
					+ "totalDiamond,postTime,wordNum,readNum,commentNum,likeNum) " + "values(?,?,?,?,?,?,?,?,?)";
			try {
				Jdbc.update(sql, author, title, content, totalDiamond, date, wordNum, readNum, commentNum, likeNum);
			} catch (Exception e) {

			}


	}
}}