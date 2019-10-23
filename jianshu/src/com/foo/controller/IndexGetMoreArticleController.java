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
		System.out.println("前台回传:"+exclude);
		// 调用下层(业务层), 得到数据
		List<Article> listArticle = articleService.getMoreRecommedArticles(exclude);
		//将list转换成json格式数据	
		String jsonStr = JSON.toJSON(listArticle).toString();
		// 以响应头的方式告诉浏览器: 我给你的不是html了哦, 是json类型的文本, 请你以正确的方式来解析和处理!
		resp.setContentType("application/json;charset=UTF-8");
		
		// 输出JSON到浏览器
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
