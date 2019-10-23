package com.foo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.foo.dao.Jdbc;
import com.foo.dao.articleDao;
import com.foo.entity.Article;

public class ArticleService {
    private articleDao articleDao=new articleDao();
    Map<Integer, Integer> map=new HashMap<>();
    
    public List<Article> getRecommedArticles(){

//    	//存储获取到的十条文章
//    	List<Article> listArticle=new ArrayList<>();
//    	//判断是否已经获取到过
//    	for(int i=0;i<10;i++) {
//    		Article article=articleDao.selectRandom();
//    		int id=article.getArticleId();
//    		if(!map.containsKey(id)) {
//    			map.put(id,0);
//    			listArticle.add(article);    			
//    		}
//    	}
//    	return listArticle;
    	try {
			return articleDao.selectRandom();
		} finally {
			Jdbc.close();
		}
    }

    public List<Article> getMoreRecommedArticles(int[] exclude) {
    	try {
			return articleDao.selectRandomExclude(exclude);
		} finally {
			Jdbc.close();
		}
	}

	public Article getArticleById(int articleId) {
    	return articleDao.getArticleById(articleId);
    }

}
