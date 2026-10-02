package com.Shuan.spring_boot_study.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import com.Shuan.spring_boot_study.model.UserStatus;

@Entity
@Table(name = "app_users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false ,length = 50)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private  UserStatus status = UserStatus.ACTIVE;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    protected User() {

    }

    public User(String name , String email) {
        this.name = name;
        this.email = email;
    }

    public  void changeName(String name) {
        this.name = name;
    }
    public void changeEmail(String email) {
        this.email = email;
    }
    public  void changeStatus(UserStatus status ) {
        this.status = status;
    }

    public  Long getId() {
        return  id;
    }
    public  String getName() {
        return  name;
    }
    public  String getEmail() { return email;}

    public  UserStatus getStatus() {
        return  status;
    }
    public  Instant getCreatedAt() {
        return  createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
