package com.grbk.blog.service;

import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);

    //获取博客并把它的内容转换成html
    Blog getBlogAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    //根据搜索条件查询博客列表
    Page<Blog> listBlog(String query,Pageable pageable);

    //根据标签id查询博客列表
    Page<Blog> listBlog(Long tagId,Pageable pageable);

    List<Blog> listBlogTop(Integer size);

    //查询每一年所对应的博客列表
    Map<String,List<Blog>> archiveBlog();

    //查询博客总条数
    Long countBlog();

    Blog saveBlog(Blog blog);

    Blog update(Long id,Blog blog);

    void delete(Long id);
}
