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
//    public  ResponseEntity<User> findById(@PathVariable Long id) {
//       Optional<User> user = userService.findById(id);
//       if (user.isPresent()) {
//           return  ResponseEntity.ok(user.get());
//       }
//       return ResponseEntity.notFound().build();
//   }
   public  User findBy(@PathVariable Long id) {
       return userService.findByIdOrThrow(id);
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
    public User create( @Valid @RequestBody CreateUserRequest request) {
       return  userService.create(request.name());
   }

   @PutMapping("/{id}")
    public  User update(
           @PathVariable Long id ,
           @Valid @RequestBody UpdateUserRequest request
   ) {
       return userService.update(id, request.name());
   }

   @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
       userService.delete(id);
   }

}
