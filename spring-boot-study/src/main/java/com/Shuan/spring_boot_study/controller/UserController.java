package com.Shuan.spring_boot_study.controller;
import com.Shuan.spring_boot_study.dto.UpdateUserRequest;
import com.Shuan.spring_boot_study.model.User;
import com.Shuan.spring_boot_study.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import com.Shuan.spring_boot_study.dto.CreateUserRequest;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;

import com.Shuan.spring_boot_study.dto.UserResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/users")
public class UserController {
   private final UserService userService;
   public UserController(UserService userService) {
       this.userService = userService;
   }

//   @GetMapping
//    public List<UserResponse> findAll() {
//       return  userService.findAll().stream()
//               .map(UserResponse::from)
//               .toList();
//   }

   @GetMapping("/{id}")
   public  UserResponse findBy(@PathVariable Long id) {
       return UserResponse.from(userService.findByIdOrThrow(id));
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create( @Valid @RequestBody CreateUserRequest request) {

       return  UserResponse.from(userService.create(request.name()));
   }

   @PutMapping("/{id}")
    public  UserResponse update(
           @PathVariable Long id ,
           @Valid @RequestBody UpdateUserRequest request
   ) {
       return UserResponse.from(userService.update(id, request.name()));
   }

   @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
       userService.delete(id);
   }

   @GetMapping
    public  Page<UserResponse> findAll(Pageable pageable) {
       return userService.findAll(pageable).map(UserResponse::from);
   }

   @GetMapping("/search")
    public Page<UserResponse> search(
            @RequestParam String name,
            Pageable pageable
   ) {
       return  userService.searchByName(name, pageable).map(UserResponse::from);
   }
}
