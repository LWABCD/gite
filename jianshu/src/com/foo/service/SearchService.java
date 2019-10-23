package com.foo.service;

import java.util.List;

import com.foo.dao.SearchDao;
import com.foo.entity.Article;

public class SearchService {

	private SearchDao searchDao=new SearchDao();
	
	public List<Article> search(String searchText){
		System.out.println("----"+searchDao.search(searchText));
		return searchDao.search(searchText);
	}
}
