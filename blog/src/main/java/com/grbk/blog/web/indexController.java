package com.grbk.blog.web;

import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.service.BlogService;
import com.grbk.blog.service.TagService;
import com.grbk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            , Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listBlogTop(8));
        return "index";
    }

    @PostMapping("/search")
    public String searchBlogs(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            ,@RequestParam String query, Model model){
        //因为repository层不会去处理查询的字符串，所以在这里要加上%%
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String getBlog(@PathVariable Long id,Model model){
        model.addAttribute("blog", blogService.getBlogAndConvert(id));
        return "blog";
    }

    //加载页面底部的博客列表
    @GetMapping("/footer/newblogs")
    public String newblogs(Model model){
        model.addAttribute("footerblogs", blogService.listBlogTop(3));
        return "_fragments :: newbloglist";
    }
}
