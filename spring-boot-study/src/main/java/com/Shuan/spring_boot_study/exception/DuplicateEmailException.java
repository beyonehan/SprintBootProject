package com.Shuan.spring_boot_study.exception;

public class DuplicateEmailException extends RuntimeException {
    public  DuplicateEmailException(String email) {
        super("邮箱已被使用：" + email);
    }
}
