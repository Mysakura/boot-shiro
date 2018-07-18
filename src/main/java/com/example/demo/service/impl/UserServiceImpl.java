package com.example.demo.service.impl;

import com.example.demo.common.PasswordUtils;
import com.example.demo.common.TempStorage;
import com.example.demo.common.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @program: boot-shiro
 * @description:
 * @author: 001977
 * @create: 2018-07-17 19:52
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public void addUser(User user) {
        user.setPassword(PasswordUtils.saltAndMd5(user.getUsername(),user.getPassword()));  // 加密
        TempStorage.getInstance().add(user);
    }

    @Override
    public User login(User user) {
        user.setPassword(PasswordUtils.saltAndMd5(user.getUsername(),user.getPassword()));  // 加密
        User u = TempStorage.getInstance().getUser(user.getUsername());
        if (u == null || !check(user, u)){
            return null;
        }
        return u;
    }

    private boolean check(User a, User b){
        if (a.getUsername().equals(b.getUsername()) && a.getPassword().equals(b.getPassword())){
            return true;
        }
        return false;
    }
}
