package com.Shuan.spring_boot_study.repository;
import com.Shuan.spring_boot_study.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository  extends JpaRepository<User, Long> {
      Page<User> findByNameContainingIgnoreCase(String name,Pageable pageable );
}
