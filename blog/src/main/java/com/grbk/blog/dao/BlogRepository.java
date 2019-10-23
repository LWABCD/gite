package com.grbk.blog.dao;

import com.grbk.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Max;
import java.util.List;

//继承JpaSpecificationExecutor<Blog>可以使用到一些组合查询
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    //?后面的数字代表第几个参数，这里的1就代表第一个参数query
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(String query, Pageable pageable);

    @Transactional
    @Modifying   //update需要使用该注解，要用该注解就要加@Transactional，事务也可以在service层加，那样service层的事务就会覆盖掉dao层的事务
    @Query("update Blog b set b.views=b.views+1 where id=?1")
    int updateViews(Long id);

    //查询年份并分组，function('date-format',b.updateTime,'%Y')：jpa中的函数
    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by year desc ")
    List<String> findGroupYear();

    //查询某个年份下的博客列表
    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1")
    List<Blog> findByYear(String year);
}
