package com.foo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;

import com.alibaba.fastjson.JSON;
import com.foo.entity.User;
import com.foo.service.UserService;

import net.sf.json.JSONArray;

@WebServlet("/index-get-users")
public class IndexGetAuthorController extends HttpServlet {
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute("user");
		int uid=-1;   //����û�û�е�¼uid�͸�ֵΪ-1
		if(user!=null) {
			uid=user.getUid();
		}
		// �����²�(ҵ���), �õ�û����ע���������б�
		List<User> users = userService.getRecommedAuthors(uid);
		//��listת����json��ʽ����	
		String jsonStr = JSON.toJSON(users).toString();
		// ����Ӧͷ�ķ�ʽ���������: �Ҹ���Ĳ���html��Ŷ, ��json���͵��ı�, ��������ȷ�ķ�ʽ�������ʹ���!
		resp.setContentType("application/json;charset=UTF-8");
		
		// ���JSON�������
		PrintWriter pw = resp.getWriter();
		
		pw.write(jsonStr);
		
		pw.flush();
		
		pw.close();
		
       // System.out.println("ʮ���û���"+jsonStr);	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
