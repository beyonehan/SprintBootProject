package com.Shuan.spring_boot_study.exception;

public class InvalidCredentialsException extends   RuntimeException {
    public InvalidCredentialsException(Long id) {
        super("用户不存在，id =" + id);
    };
}
