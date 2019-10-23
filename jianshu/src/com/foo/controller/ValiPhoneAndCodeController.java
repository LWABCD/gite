package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.foo.service.UserService;
import com.foo.util.randomdate;

/**
 * ��֤�ֻ����Ƿ����
 * 
 * @author leibin
 *
 */
@WebServlet("/valiPhoneAndCodeController")
public class ValiPhoneAndCodeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String result = "";
		// ��ȡע����洫����������
		String phone = request.getParameter("cal");
		String vcode = request.getParameter("calyzm");

		String sessionvcode = (String) (session.getAttribute("valicodeString"));

		boolean checkPhone = userService.checkPhoneUnique(phone);
		if (checkPhone == false) {
			if (vcode.equals(sessionvcode)) {
				// ��֤�����
				result = "1";
			} else {
				result = "2";
			}
		} else {
			// �ֻ����Ѵ���
			result = "3";
		}

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
