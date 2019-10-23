package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.foo.entity.User;


@WebServlet("/loginStateController")
public class LoginStateController extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		User user=(User)session.getAttribute("user");

		System.out.println("scasjkncvkv"+user);
		String json="";
		if (user==null) {
		//Î´µÇÂ¼	
			json="{\"loginState\":\"false\"}";
		}else {
			//ÒÑµÇÂ¼
			json="{\"loginState\":\"true\",\"nickName\":\""+user.getNickName()+"\"}";
		}
		 System.out.println(JSON.toJSON(json).toString());
		resp.setContentType("application/json;charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter printWriter=resp.getWriter();
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
       
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
