package com.Shuan.spring_boot_study;

import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    void searchesNameIgnoringCase() {
        userRepository.save(new User("Emma", "emma@example.com", "test-password-hash"));
        userRepository.save(new User("Bob", "bob@example.com", "test-password-hash"));

        Page<User> result = userRepository.findByNameContainingIgnoreCase(
                "EM",
                PageRequest.of(0,10)

        );
        assertEquals(1, result.getTotalElements());
        assertEquals("Emma", result.getContent().getFirst().getName());

    }
}
