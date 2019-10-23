package com.grbk.blog.web;

import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.pojo.Tag;
import com.grbk.blog.service.BlogService;
import com.grbk.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 标签
 */

@Controller
public class TagShowController {

    @Autowired
    private TagService TagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String Tags(@PageableDefault(size = 8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            ,@PathVariable Long id, Model model){
        List<Tag> tags=TagService.listTagTop(10000);
        //如果没有传id过来就默认展示第一个分类的博客列表
        if(id==-1){
            id=tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
