package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;

import com.alibaba.fastjson.JSON;
import com.foo.entity.User;
import com.foo.service.UserService;

import net.sf.json.JSONArray;

@WebServlet("/index-get-users")
public class IndexGetAuthorController extends HttpServlet {
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute("user");
		int uid=-1;   //如果用户没有登录uid就赋值为-1
		if(user!=null) {
			uid=user.getUid();
		}
		// 调用下层(业务层), 得到没被关注过的作者列表
		List<User> users = userService.getRecommedAuthors(uid);
		//将list转换成json格式数据	
		String jsonStr = JSON.toJSON(users).toString();
		// 以响应头的方式告诉浏览器: 我给你的不是html了哦, 是json类型的文本, 请你以正确的方式来解析和处理!
		resp.setContentType("application/json;charset=UTF-8");
		
		// 输出JSON到浏览器
		PrintWriter pw = resp.getWriter();
		
		pw.write(jsonStr);
		
		pw.flush();
		
		pw.close();
		
       // System.out.println("十个用户："+jsonStr);	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
