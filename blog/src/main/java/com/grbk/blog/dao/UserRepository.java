package com.grbk.blog.dao;

import com.grbk.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository中的参数分别是java实体对象和主键的类型，继承过后直接就可以使用里面的方法了
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
