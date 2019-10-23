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

		// 上面两句加上首页中文乱码
		request.setCharacterEncoding("UTF-8");
		String boxString = request.getParameter("box");
		System.out.println(boxString);
		String phone = request.getParameter("cal");		
		String pwdString=request.getParameter("pwd");
		
		System.out.println("phone:"+phone+"-----pwd:"+pwdString);
		if (phone!=""&&pwdString!="") {
			// 验证手机号是否存在
			String passWord = MD5.encrypt(pwdString);
			boolean checkPhone = userService.checkPhoneUnique(phone);
			if (checkPhone == false) {
				request.setAttribute("errorMsg", "手机号不存在，请注册！");
			} else {// 手机号存在
				User user = userService.login(phone, passWord);
				// 如果用户输入的昵称在数据库中不存在，则重新登陆
				if (user == null) {
					// 返回错误信息
					request.setAttribute("errorMsg", "手机号或密码错误，请重新输入！");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
					// 如果用户存在，则判断密码是否正确，如果正确则跳到登陆成功页面
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
			request.setAttribute("errorMsg", "手机号或密码不能为空！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
