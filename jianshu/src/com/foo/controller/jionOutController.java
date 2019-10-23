package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foo.entity.User;


@WebServlet("/jionOutController")
public class jionOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�û��˳�ʱҪɾ��cookie�е��û���Ϣ
		Cookie ckPhone=new Cookie("scsac",null);
		Cookie ckPwd=new Cookie("axax",null);
		ckPhone.setMaxAge(0); 
		ckPhone.setMaxAge(0); 
		response.addCookie(ckPhone);
		response.addCookie(ckPwd);
		        
		//ɾ��session�е��û���Ϣ
		HttpSession session = request.getSession();
		     session.removeAttribute("user");
		response.sendRedirect(request.getContextPath() + "/index.html");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
