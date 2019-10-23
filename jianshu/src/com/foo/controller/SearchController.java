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
import com.foo.service.SearchService;

import sun.print.resources.serviceui;

@WebServlet("/search")
public class SearchController extends HttpServlet{

	private SearchService searchService=new SearchService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//ÄÃµ½ËÑË÷ÄÚÈÝ
		String searchText=req.getParameter("searchText");
		searchText="%"+searchText+"%";
		System.out.println(searchText);
		List<Article> artileList=searchService.search(searchText);
		String articleListStr=JSON.toJSON(artileList).toString();
        System.out.println(articleListStr);
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.write(articleListStr);
		pw.flush();
		pw.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
