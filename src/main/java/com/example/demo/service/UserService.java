package com.example.demo.service;

import com.example.demo.common.entity.User;

/**
 * @program: boot-shiro
 * @description:
 * @author: 001977
 * @create: 2018-07-17 19:37
 */
public interface UserService {

    void addUser(User user);

    User login(User user);

}
