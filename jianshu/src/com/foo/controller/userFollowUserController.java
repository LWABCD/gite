package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.foo.entity.User;
import com.foo.service.UserService;
import com.sun.javafx.image.IntPixelGetter;

//��עcontroller

@WebServlet("/userFollowUserController")
public class userFollowUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService=new UserService();
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��session�л�ȡ����ǰ��½�û�����Ϣ
		HttpSession session = request.getSession();
		User user=(User)session.getAttribute("user");
		String json="";
		//�û����ڵ�½״̬
		if(user!=null) {
			int userA=user.getUid();
			//��ȡ����ע�û���uid
			int userB=Integer.parseInt(request.getParameter("uid"));
			//�û���ע��ʱ��
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
			String followTime=dateFormat.format(date);
			userService.saveUserFollowUserInfo(userA, userB,followTime);
			json="{\"loginState\":\"true\"}";
		}else {
			json="{\"loginState\":\"false\"}";
		}
		PrintWriter printWriter=response.getWriter();
		printWriter.write(json);
		printWriter.flush();
		printWriter.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
