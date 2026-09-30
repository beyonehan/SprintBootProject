package com.Shuan.spring_boot_study.exception;

public class UserNotFoundException extends RuntimeException {

    public  UserNotFoundException(Long id) {
        super("用户不存在，id =" + id);
    }
}
