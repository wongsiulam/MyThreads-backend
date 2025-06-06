package com.socialconnect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.socialconnect.service.UserService;
import com.socialconnect.entity.User;
import com.socialconnect.dto.RegisterRequest;
import com.socialconnect.dto.LoginResponse;
import com.socialconnect.util.JwtUtil;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request) {
        return userService.register(request.getPhone(), request.getPassword());
    }
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody RegisterRequest request) {
        User user = userService.login(request.getPhone(), request.getPassword());
        String token = JwtUtil.generateToken(user.getId());
        return new LoginResponse(token, user);
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

    @GetMapping("/me")
    public User getCurrentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("currentUserId");
        if (userId == null) {
            throw new RuntimeException("未登录或Token无效");
        }
        return userService.getUserById(userId);
    }
}
