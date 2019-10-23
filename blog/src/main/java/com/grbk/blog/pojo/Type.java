package com.grbk.blog.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    private Long id;   //分类id
    @NotBlank(message = "分类名不能为空")   //前端传递给controller的该属性不能为空，也可以使用js在前台就进行判断
    private String name;   //分类名

    @OneToMany(mappedBy = "type")   //一对多，一种类型对应多篇博客,指定Blog为关系维护端，Type为被维护端
    private List<Blog> blogs=new ArrayList<>();   //该分类下的博客列表

    public Type() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
