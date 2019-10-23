package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.foo.entity.User;
import com.foo.service.UserService;
import com.foo.util.MD5;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// �������������ҳ��������
		request.setCharacterEncoding("UTF-8");
		String boxString = request.getParameter("box");
		System.out.println(boxString);
		String phone = request.getParameter("cal");		
		String pwdString=request.getParameter("pwd");
		
		System.out.println("phone:"+phone+"-----pwd:"+pwdString);
		if (phone!=""&&pwdString!="") {
			// ��֤�ֻ����Ƿ����
			String passWord = MD5.encrypt(pwdString);
			boolean checkPhone = userService.checkPhoneUnique(phone);
			if (checkPhone == false) {
				request.setAttribute("errorMsg", "�ֻ��Ų����ڣ���ע�ᣡ");
			} else {// �ֻ��Ŵ���
				User user = userService.login(phone, passWord);
				// ����û�������ǳ������ݿ��в����ڣ������µ�½
				if (user == null) {
					// ���ش�����Ϣ
					request.setAttribute("errorMsg", "�ֻ��Ż�����������������룡");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					// ����û����ڣ����ж������Ƿ���ȷ�������ȷ��������½�ɹ�ҳ��
				} else {
					request.getSession().setAttribute("user", user);
					if (boxString == null) {
						// session.removeAttribute("user");
						Cookie cookie_phone = new Cookie("scsac", null);
						Cookie cookie_pwd = new Cookie("axax", null);
						cookie_phone.setMaxAge(0);
						cookie_pwd.setMaxAge(0);
						response.addCookie(cookie_pwd);
						response.addCookie(cookie_phone);
						response.sendRedirect(request.getContextPath() + "/index.html");
					} else {
						Cookie cookie_phone = new Cookie("scsac", user.getPhone());
						Cookie cookie_pwd = new Cookie("axax", user.getPwd());
						cookie_phone.setMaxAge(10 * 24 * 60);
						cookie_pwd.setMaxAge(10 * 24 * 60);

						response.addCookie(cookie_pwd);
						response.addCookie(cookie_phone);
						response.sendRedirect(request.getContextPath() + "/index.html");
					}

				}
			}
		}else {
			request.setAttribute("errorMsg", "�ֻ��Ż����벻��Ϊ�գ�");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
