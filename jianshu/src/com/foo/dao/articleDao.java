package com.foo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.foo.entity.Article;
import com.foo.entity.User;

public class articleDao {
	int[] exclude = {};

	// 从数据库查询十条文章
	public List<Article> selectRandom() {
		String sql = "SELECT * FROM `article` WHERE articleId >= (SELECT FLOOR(RAND() * (SELECT MAX(articleId) FROM `article`)))  ORDER BY articleId LIMIT 10";
		List<Article> list = Jdbc.select(sql, new BeanListHandler<Article>(Article.class));
	
		return list;
	}

	// 从数据库查询十条文章（除去上面10条文章）
	public List<Article> selectRandomExclude(int[] exclude) {
		String sql = "SELECT * FROM article WHERE articleId >= (SELECT FLOOR(RAND() * (SELECT MAX(articleId) FROM `article`)))"+this.generateInStatement(exclude)+"  ORDER BY articleId LIMIT 10";
		Object[] params=new Object[exclude.length];
		for (int i = 0; i < exclude.length; i++) {
			params[i]=exclude[i];
		}
		return Jdbc.select(sql, new BeanListHandler<Article>(Article.class),params);
	}

	private String generateInStatement(int[] exclude) {
		if (exclude != null && exclude.length > 0) {
			String in = " AND articleId not in (";
			for (int i = 0; i < exclude.length; i++) {
				in += "?";
				if (i < exclude.length - 1)
					in += ",";
			}
			in += ")";
			return in;
		}
		return "";
	}

		
		public Article getArticleById(int articleId) {
			String sql="select * from article where articleId=?";
			return Jdbc.select(sql, new BeanHandler<Article>(Article.class),articleId);
		}
}
