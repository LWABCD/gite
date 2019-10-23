package com.grbk.blog.dao;

import com.grbk.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> {

    Type getTypeByName(String name);

    @Query("select t from Type t")   //自定义查询，从Tyoe对象查询，Pageable做参数是因为它里面有一页的大小
    List<Type> findTop(Pageable pageable);
}
