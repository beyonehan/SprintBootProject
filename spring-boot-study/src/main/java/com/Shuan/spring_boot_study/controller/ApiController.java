package com.Shuan.spring_boot_study.controller;
import org.springframework.web.bind.annotation.*;
import com.Shuan.spring_boot_study.dto.GreetingResponse;

@RestController
@RequestMapping("/api")
public class ApiController {
   @GetMapping("/hello")
    public String hello() {
       return "Hello API";
   }

   @GetMapping("/greeting")
    public String greeting( @RequestParam(defaultValue = "default") String name) {
       return "greeting"  + name;
   }
   @GetMapping("/user/{id}")
    public String findName( @PathVariable(required = true) long id ) {
       return  "正在查询" + id;
   }

   @GetMapping("/greeting.json")
    public GreetingResponse greetingJson(@RequestParam(defaultValue = "default") String name) {
       return  new GreetingResponse("hello" + name , name);
   }
}
