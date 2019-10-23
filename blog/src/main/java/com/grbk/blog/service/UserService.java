package com.grbk.blog.service;

import com.grbk.blog.pojo.User;

public interface UserService {

    User checkUser(String userName, String password);
}
