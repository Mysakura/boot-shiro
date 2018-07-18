package com.example.demo.exception;

/**
 * @program: boot-shiro
 * @description:
 * @author: 001977
 * @create: 2018-07-17 19:53
 */
public class SimpleException extends RuntimeException {

    private String msg;

    public SimpleException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SimpleException() {
    }
}
