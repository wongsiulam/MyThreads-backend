package com.socialconnect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.socialconnect.service.UserService;
import com.socialconnect.entity.User;
import com.socialconnect.dto.RegisterRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request.getPhone(), request.getPassword());
    }
    
    @PostMapping("/login")
    public User login(@RequestBody RegisterRequest request) {
        return userService.login(request.getPhone(), request.getPassword());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{update}")
    public Boolean updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @GetMapping("/check-phone")
    public boolean checkPhoneExists(@RequestParam String phone) {
        return userService.checkPhoneExists(phone);
    }
}
