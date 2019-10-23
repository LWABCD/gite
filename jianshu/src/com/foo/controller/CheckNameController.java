package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.foo.service.UserService;

@WebServlet("/checkNameController")
public class CheckNameController extends HttpServlet {
	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("--------------------------");
		String result = "true";
		// ��ȡע����洫����������
		String nickName = request.getParameter("nickName");
		if (userService.checkNickNameUnique(nickName)) {
			// �ǳƴ���
			result = "false";
			System.out.println("�ǳ��Ѵ���");

		}
		// ���������

		response.setContentType("text/html");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(result.getBytes());
		outputStream.close();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
