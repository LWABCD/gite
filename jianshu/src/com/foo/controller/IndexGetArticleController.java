package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.foo.entity.Article;
import com.foo.service.ArticleService;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class IndexGetArticleController
 */
@WebServlet("/index-get-articles")
public class IndexGetArticleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticleService articleService=new ArticleService();
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//�Ӻ�̨��ȡ��ʮ������
		List<Article> listArticle=articleService.getRecommedArticles();

		//��ʮ������ʵ��ת��Ϊjson��ʽ
		String jsonString=JSON.toJSON(listArticle).toString();
		//System.out.println(jsonString);
		//��json��ʽ��������Ӧ��ǰ��
		PrintWriter out = response.getWriter();
		out.print(jsonString);
		out.close();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
