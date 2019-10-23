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
import com.foo.entity.User;
import com.foo.service.ArticleService;
import com.foo.service.UserService;

/**
 * Servlet implementation class IndexGetArticleByIdController
 */
@WebServlet("/IndexGetArticleByIdController")
public class IndexGetArticleByIdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private ArticleService articleService=new ArticleService();
	private UserService userService = new UserService();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int articleId=Integer.parseInt(request.getParameter("id"));
		System.out.println(articleId);
		
		Article articles=articleService.getArticleById(articleId);
		User users = userService.getAuthorById(articles.getAuthor());
		
		  //存入request域
        request.setAttribute("articles",articles);
        request.setAttribute("users",users);


		 //转发到list.jsp页面
        request.getRequestDispatcher("/list.jsp").forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
