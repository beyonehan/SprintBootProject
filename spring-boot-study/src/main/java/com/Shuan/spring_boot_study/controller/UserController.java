package com.Shuan.spring_boot_study.controller;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.service.UserService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
   private final UserService userService;
   public UserController(UserService userService) {
       this.userService = userService;
   }

   @GetMapping
    public List<User> findAll(){
       return  userService.findAll();
   }

   @GetMapping("/{id}")
    public  ResponseEntity<User> findById(@PathVariable Long id) {
       Optional<User> user = userService.findById(id);
       if (user.isPresent()) {
           return  ResponseEntity.ok(user.get());
       }
       return ResponseEntity.notFound().build();
   }
}
