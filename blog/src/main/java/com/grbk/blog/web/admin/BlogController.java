package com.grbk.blog.web.admin;

import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.pojo.Blog;
import com.grbk.blog.pojo.User;
import com.grbk.blog.service.BlogService;
import com.grbk.blog.service.TagService;
import com.grbk.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    //点击导航栏的博客标签
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 3,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("types", typeService.listType());   //前端第一次请求博客列表时就把分类列表列表传递过去加载
        model.addAttribute("page", blogService.listBlog(pageable));
        return "admin/blogs";
    }

    //点击搜索按钮
    @PostMapping("/blogs/search")
    public String searchBlogs(@PageableDefault(size = 3,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable
            ,BlogQuery blog,Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        //把数据传递给前端fragment=blogList的片段，前端页面只对该片段进行异步刷新
        return "admin/blogs :: blogList";
    }

    //点击新增按钮
    @GetMapping("/blogs/input")
    public String input(Model model)throws Exception{
        model.addAttribute("blog", new Blog());
        model.addAttribute("type", typeService.listType());
        model.addAttribute("tag", tagService.listTag());
        return "admin/blogs-input";
    }

    //提交表单，新增和编辑都调用此方法，都使用blogs-input.html页面
    @PostMapping("/blogs/add")
    public String addBlog(Blog blog, RedirectAttributes attributes, HttpSession session){
        blog.setUser((User)session.getAttribute("user"));   //注入作者信息
        blog.setType(typeService.getType(blog.getType().getId()));   //注入博客分类，因为前端只传了一个分类id过来
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b=blogService.saveBlog(blog);
        if(b==null){
            attributes.addFlashAttribute("messagge", "操作失败");
        }else{
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    //点击编辑按钮
    @GetMapping("blogs/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        Blog blog=blogService.getBlog(id);
        blog.initTagIds();
        model.addAttribute("blog", blog);
        model.addAttribute("type", typeService.listType());
        model.addAttribute("tag", tagService.listTag());
        return "admin/blogs-input";
    }

    //点击删除按钮
    @GetMapping("blogs/delete/{id}")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.delete(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }
}
