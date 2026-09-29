package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.respository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    public Optional<User> findById(long id) {
        return  userRepository.findById(id);
    }
}
