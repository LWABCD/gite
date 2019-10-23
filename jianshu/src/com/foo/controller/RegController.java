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
		//��ȡע����洫����������
		String nickName=request.getParameter("username");
		
		String phone=request.getParameter("cal");
		
		String passWord=request.getParameter("pwd");
		
		String vcode=request.getParameter("calyzm");
		
		String sessionvcode=(String)(session.getAttribute("valicodeString"));
	
		if (nickName==""||phone==""||passWord==""||vcode=="") {
			request.setAttribute("errorMsg", "��������Ϣ���ύ��");
			request.getRequestDispatcher("/reg.jsp").forward(request, response);
		}else {
			boolean checkPhone=userService.checkPhoneUnique(phone);
			//������ֻ�����ÿ��ע���
			if(checkPhone==false){
				System.out.println("�ֻ��Ų����ڣ�����ע��"+nickName);
				//����ǳ��Ѿ����ù�����֤�벻�ԣ�ֱ������ע��ҳ������ע�ᣬ���򱣴���û�����Ϣ��������¼ҳ
				System.out.println(userService.checkNickNameUnique(nickName));
				
				if(userService.checkNickNameUnique(nickName)) {
					//�ǳƴ���
					result="1";
					 request.setAttribute("result",result);
					//System.out.println("�ǳ��Ѵ���");
					request.getRequestDispatcher("/reg.jsp").forward(request,response);				
				}else {
					//�ǳƲ����ڣ��ȶ���֤��
					if(vcode.equals(sessionvcode)) {
						//�˴�if�����ж�Ҫ��vcode.equals(sessionvcode)��������==�����������
						//���session�е���֤��
						session.removeAttribute("valicodeString");
						//��֤ͨ����ע��ɹ�
						String regTime=randomdate.sysTime();
						userService.saveRegInfo(nickName, phone, passWord,regTime);
						response.sendRedirect(request.getContextPath()+"/login.jsp");
						//request.getRequestDispatcher("/login.jsp").forward(request,response);
					}else {
						//��֤�����
						result="3";
						 request.setAttribute("result",result);
						request.getRequestDispatcher("/reg.jsp").forward(request,response);				
						System.out.println("��֤�����");
					}			
				}
			
			} else {
				//�ֻ�����
				result="2";
				 request.setAttribute("result",result);
				//System.out.println("�ֻ����Ѵ���");
				request.getRequestDispatcher("/reg.jsp").forward(request,response);
			}
			
			
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
