package com.foo.dao;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.foo.entity.Article;

public class SearchDao {

	public List<Article> search(String searchText){
		String sql="select * from article where title like ? or content like ?";
		return Jdbc.select(sql, new BeanListHandler<Article>(Article.class), searchText,searchText);
	}
}
