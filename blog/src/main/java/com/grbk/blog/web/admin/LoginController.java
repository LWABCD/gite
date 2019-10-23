package com.grbk.blog.web.admin;

import com.grbk.blog.pojo.User;
import com.grbk.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录controller
 */

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){
        User user=userService.checkUser(username, password);
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }else{
            //向重定向的页面返回错误提示
            attributes.addAttribute("message", "用户名或密码错误");
            //重定向到登录页面
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String loginout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
