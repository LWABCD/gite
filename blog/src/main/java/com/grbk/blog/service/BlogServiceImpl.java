package com.grbk.blog.service;

import com.grbk.blog.NotFoundException;
import com.grbk.blog.dao.BlogRepository;
import com.grbk.blog.extralpojo.BlogQuery;
import com.grbk.blog.pojo.Blog;
import com.grbk.blog.pojo.Type;
import com.grbk.blog.util.MarkdownUtils;
import com.grbk.blog.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.security.PublicKey;
import java.util.*;

/**
 * 博客管理
 */

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    @Override
    public Blog getBlogAndConvert(Long id) {
        Blog blog=blogRepository.getOne(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        //因为在转换成html时会把数据库中的markdown类型的文本转换成html,因此使用了另一个blog对象jinxin操作
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(b.getContent()));
        blogRepository.updateViews(id);   //每刷新一次博客页面浏览次数加1
        return b;
    }

    @Transactional   //使用事务
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            /**
             *
             * @param root   代表查询对象
             * @param cq   查询条件的容器
             * @param cb   具体查询条件的表达式。比如相等
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //存放查询条件的数组
                List<Predicate> predicates=new ArrayList<>();
                if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                //进行查询
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query,Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join=root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlogTop(Integer size) {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0, size,sort);
        return blogRepository.findTop(pageable);   //最后拼装成的sql语句：select b from Blog b where b.recommend=true order by updatetime desc;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years=blogRepository.findGroupYear();
        Map<String,List<Blog>> map=new HashMap<>();
        for(String year:years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    public Long countBlog(){
        return blogRepository.count();   //Repository中自带的方法
    }

    //新增和编辑都调用此方法
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //新增的时候博客是没有id的
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);   //初始化浏览次数为0
        //编辑时博客有id，此时只需修改更新时间
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog update(Long id,Blog blog) {
        Blog b=blogRepository.getOne(id);
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        blogRepository.deleteById(id);
    }
}
