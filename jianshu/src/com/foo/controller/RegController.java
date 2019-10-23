package com.foo.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.foo.service.UserService;
import com.foo.util.randomdate;


@WebServlet("/regController")
public class RegController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService=new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String result="";
		//获取注册界面传过来的数据
		String nickName=request.getParameter("username");
		
		String phone=request.getParameter("cal");
		
		String passWord=request.getParameter("pwd");
		
		String vcode=request.getParameter("calyzm");
		
		String sessionvcode=(String)(session.getAttribute("valicodeString"));
	
		if (nickName==""||phone==""||passWord==""||vcode=="") {
			request.setAttribute("errorMsg", "请完善信息后提交！");
			request.getRequestDispatcher("/reg.jsp").forward(request, response);
		}else {
			boolean checkPhone=userService.checkPhoneUnique(phone);
			//如果该手机号码每被注册过
			if(checkPhone==false){
				System.out.println("手机号不存在，可以注册"+nickName);
				//如果昵称已经被用过或验证码不对，直接跳到注册页面重新注册，否则保存该用户的信息并跳到登录页
				System.out.println(userService.checkNickNameUnique(nickName));
				
				if(userService.checkNickNameUnique(nickName)) {
					//昵称存在
					result="1";
					 request.setAttribute("result",result);
					//System.out.println("昵称已存在");
					request.getRequestDispatcher("/reg.jsp").forward(request,response);				
				}else {
					//昵称不存在，比对验证码
					if(vcode.equals(sessionvcode)) {
						//此处if条件判断要用vcode.equals(sessionvcode)，不能用==，否则进不来
						//清除session中的验证码
						session.removeAttribute("valicodeString");
						//验证通过，注册成功
						String regTime=randomdate.sysTime();
						userService.saveRegInfo(nickName, phone, passWord,regTime);
						response.sendRedirect(request.getContextPath()+"/login.jsp");
						//request.getRequestDispatcher("/login.jsp").forward(request,response);
					}else {
						//验证码错误
						result="3";
						 request.setAttribute("result",result);
						request.getRequestDispatcher("/reg.jsp").forward(request,response);				
						System.out.println("验证码错误");
					}			
				}
			
			} else {
				//手机存在
				result="2";
				 request.setAttribute("result",result);
				//System.out.println("手机号已存在");
				request.getRequestDispatcher("/reg.jsp").forward(request,response);
			}
			
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
