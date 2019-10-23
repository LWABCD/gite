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


@WebServlet("/phoneValidationController")
public class PhoneValidationController extends HttpServlet {
	private UserService userService=new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		String phone=req.getParameter("p");
		
		String valicodeString=GenerateValiCode.getCode();
		
		String result="false";
		if (userService.checkPhoneUnique(phone)) {
			//�ֻ����ڣ�������֤��
			result="true";
		}else {
			//����֤��浽session��
			session.setAttribute("valicodeString", valicodeString);
//			session.setMaxInactiveInterval(60);//����Ϊ��λ		
			
		
			//������֤����û�
			SendValiCode.SendSms(valicodeString,phone);
			
		}
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
