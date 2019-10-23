package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.foo.service.UserService;
import com.foo.util.GenerateValiCode;
import com.foo.util.SendValiCode;


@WebServlet("/ ")
public class PhoneLoginController extends HttpServlet {
	private UserService userService=new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String phone=req.getParameter("p");
		String result="false";
		if (userService.checkPhoneUnique(phone)) {
			//手机存在，不发验证码
			result="true";
		}
		System.out.println("login:"+phone);
		System.out.println(result);
		resp.setContentType("text/html");
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.write(result.getBytes());
		outputStream.close();
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
