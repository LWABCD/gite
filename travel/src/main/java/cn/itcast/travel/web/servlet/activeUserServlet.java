package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code=request.getParameter("code");
        //2.调用service完成激活
        UserService userService=new UserServiceImpl();;
        boolean flag=userService.active(code);

        //3.判断标记
        String msg=null;
        if(flag){
            msg="激活成功，请<a href='login.html'>登录</a>";
        }else{
            msg="激活失败，请联系管理员";
        }

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
