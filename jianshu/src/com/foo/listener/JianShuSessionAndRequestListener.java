package com.foo.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.foo.entity.User;
import com.foo.service.UserService;

/**
 * Application Lifecycle Listener implementation class
 * JianShuSessionAndRequestListener
 *
 */
@WebListener
public class JianShuSessionAndRequestListener implements HttpSessionListener, ServletRequestListener {

	private HttpServletRequest request;
	private UserService userService=new UserService();
	
	
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		System.out.println("qqqqqqqqqqqqqqqqqqqqqq");
		Cookie[] cookies = request.getCookies();
		Cookie phoneCookie = null;
		Cookie pwdCookie = null;
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("scsac".equals(cookie.getName())) {
					phoneCookie = cookie;
				} else if ("axax".equals(cookie.getName())) {
					pwdCookie = cookie;
				}

			}
		}else {
			return;
		}		 
		//System.out.println(phoneCookie.getValue());
		//System.out.println(pwdCookie.getValue());
		//验证cookie里面的手机和密码是否正确
		if (phoneCookie==null||pwdCookie==null) {
			
			return;
		}
		
		//boolean checkPhone=userService.checkPhoneUnique(phoneCookie.getValue());
		//if (checkPhone==false) {
		//	request.setAttribute("errorMsg", "账户不存在，请重新登录！");
		//}else {//手机号存在
			User user = userService.login(phoneCookie.getValue(), pwdCookie.getValue());
			// 如果用户输入的昵称在数据库中不存在，则重新登陆
			System.out.println(user);
			if (user == null) {
				return;//

			} else {
				//视为用户已登录
				se.getSession().setAttribute("user",user);
			}
		//}

	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		// TODO Auto-generated method stub请求初始化
		this.request = (HttpServletRequest) sre.getServletRequest();
		this.request.getSession();
	}

}
