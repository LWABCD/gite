package com.foo.service;
import java.util.List;
import org.junit.Test;
import com.foo.BaseTest;
import com.foo.entity.Article;

public class TestArticleService extends BaseTest {

	private ArticleService articleService=new ArticleService();
	/***
	 * ≤‚ ‘ArticleService----getRecommedArticles()∑Ω∑®
	 */
	@Test
	public void testGetRecommedArticles() {
		List<Article> articles=articleService.getRecommedArticles();
		System.out.println(articles);
	}
	@Test
	public void testGetMoreRecommedArticles() {
		int[] exclude= {2,3,4,5,6,7,8,9,10,15};
		List<Article> articles=articleService.getMoreRecommedArticles(exclude);
		System.out.println(articles);
	}
}
