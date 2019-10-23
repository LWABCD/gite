package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.foo.entity.Article;
import com.foo.entity.User;
import com.foo.service.ArticleService;
import com.foo.service.UserService;
@WebServlet("/get-more-article")
public class IndexGetMoreArticleController extends HttpServlet {
	private ArticleService articleService = new ArticleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] idArray = req.getParameterValues("id[]");
		System.out.println(Arrays.toString(idArray));
		
		int[] exclude=new int[idArray.length];
		
		for(int i=0;i<idArray.length;i++){  

		  exclude[i]=Integer.parseInt(idArray[i]);

		}
		System.out.println("ǰ̨�ش�:"+exclude);
		// �����²�(ҵ���), �õ�����
		List<Article> listArticle = articleService.getMoreRecommedArticles(exclude);
		//��listת����json��ʽ����	
		String jsonStr = JSON.toJSON(listArticle).toString();
		// ����Ӧͷ�ķ�ʽ���������: �Ҹ���Ĳ���html��Ŷ, ��json���͵��ı�, ��������ȷ�ķ�ʽ�������ʹ���!
		resp.setContentType("application/json;charset=UTF-8");
		
		// ���JSON�������
		PrintWriter pw = resp.getWriter();
		
		pw.write(jsonStr);
		
		pw.flush(); 
		
		pw.close();
		
        System.out.println(jsonStr);	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
