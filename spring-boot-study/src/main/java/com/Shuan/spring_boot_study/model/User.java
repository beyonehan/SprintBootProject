package com.Shuan.spring_boot_study.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.Column;

@Entity
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false ,length = 50)
    private String name;
    protected User() {

    }

    public User(String name) {
        this.name = name;
    }

    public  Long getId() {
        return  id;
    }
    public  String getName() {
        return  name;
    }
}

