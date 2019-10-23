package com.grbk.blog.web;

import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.pojo.Blog;
import com.grbk.blog.pojo.Type;
import com.grbk.blog.service.BlogService;
import com.grbk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 分类
 */

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            ,@PathVariable Long id, Model model){
        List<Type> types=typeService.listTypeTop(10000);
        //如果没有传id过来就默认展示第一个分类的博客列表
        if(id==-1){
            id=types.get(0).getId();
        }
        BlogQuery blogQuery=new BlogQuery();
        blogQuery.setTypeId(id);   //根据分类id查询博客列表
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
