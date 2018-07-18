package com.example.demo.common;

import com.example.demo.common.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: boot-shiro
 * @description:
 * @author: 001977
 * @create: 2018-07-17 19:47
 */
public class TempStorage {
    private TempStorage() {
    }

    private static class Inner{
        private static TempStorage instance = new TempStorage();
    }

    public static TempStorage getInstance(){
        return Inner.instance;
    }

    private ConcurrentHashMap<String,User> map = new ConcurrentHashMap<>();

    public void add(User user){
        map.put(user.getUsername(), user);
    }

    public User getUser(String username){
        return map.get(username);
    }

    public List<User> getMap() {
        List<User> list = new ArrayList<>(map.values());
        return list;
    }
}
