package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.exception.UserNotFoundException;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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

    public User create(String name) {
        User user  = new User(name);
        return userRepository.save(user);
    }

    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User update(Long id, String name) {
        User user = findByIdOrThrow(id);
        user.changeName(name.strip());
        return user;
    }

    @Transactional
    public void delete(Long id) {
        User user = findByIdOrThrow(id);
        userRepository.delete(user);
    }
}
