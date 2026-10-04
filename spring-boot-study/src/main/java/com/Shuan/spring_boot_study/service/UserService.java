package com.Shuan.spring_boot_study.service;

import com.Shuan.spring_boot_study.exception.UserNotFoundException;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Locale;
import com.Shuan.spring_boot_study.dto.PatchUserRequest;
import com.Shuan.spring_boot_study.dto.CreateUserRequest;
import com.Shuan.spring_boot_study.dto.UpdateUserRequest;
import com.Shuan.spring_boot_study.exception.DuplicateEmailException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public List<User> findAll() {
//        return this.userRepository.findAll();
//    }

    public  Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    public  Page<User> searchByName(String name , Pageable pageable) {
        return userRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Optional<User> findById(long id) {
        return  userRepository.findById(id);
    }

    @Transactional
    public User create(String name, String email, String password) {
        String normalizedEmail = normalizeEmail(email);

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        String passwordHash = passwordEncoder.encode(password);
        User user = new User(name.strip(), normalizedEmail, passwordHash);
        return userRepository.save(user);
    }
    public User findByIdOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User update(Long id, String name, String email) {
        User user = findByIdOrThrow(id);
        String normalizedEmail = normalizeEmail(email);

        if (userRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
            throw new DuplicateEmailException(normalizedEmail);
        }

        user.changeName(name.strip());
        user.changeEmail(normalizedEmail);
        return user;
    }

    @Transactional
    public void delete(Long id) {
        User user = findByIdOrThrow(id);
        userRepository.delete(user);
    }

    private String normalizeEmail(String email) {
        return email.strip().toLowerCase(Locale.ROOT);
    }

    @Transactional
    public User patch(Long id, PatchUserRequest request) {
        User user = findByIdOrThrow(id);

        if (request.name() != null) {
            user.changeName(request.name().strip());
        }

        if (request.email() != null) {
            String normalizedEmail = normalizeEmail(request.email());
            if (userRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
                throw new DuplicateEmailException(normalizedEmail);
            }
            user.changeEmail(normalizedEmail);
        }

        if (request.status() != null) {
            user.changeStatus(request.status());
        }

        return user;
    }


}
