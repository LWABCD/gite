package com.grbk.blog.web;

import com.grbk.blog.pojo.Comment;
import com.grbk.blog.pojo.User;
import com.grbk.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 评论
 */

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Value("${comment.avatar}")
    private String avatar;

    //加载评论列表
    @GetMapping("/comments/{blogId}")
    public String commentsList(@PathVariable Long blogId, Model model){
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    //提交自己写的评论的表单
    @PostMapping("/comments")
    public String postComment(Comment comment, HttpSession session){
        User user=(User)session.getAttribute("user");
        //如果用户登录中就设置用户自己的一些属性，不然就使用定义好的
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            //comment.setNickname(user.getNickname());
            comment.setAdminComment(true);
            System.out.println("----------"+comment.isAdminComment());
        }else{
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();   //提交评论后就会重新刷新该篇博客的评论列表
    }
}
