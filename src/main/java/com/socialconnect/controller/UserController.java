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
import com.socialconnect.util.Result;
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
    public Result<?> updateUser(@RequestBody User user, 
                              @RequestParam(required = false) String oldPassword,
                              @RequestParam(required = false) String newPassword,
                              HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录或Token无效");
        }
        user.setId(userId);

        // 如果提供了密码，则进行密码修改
        if (oldPassword != null && newPassword != null) {
            boolean success = userService.changePassword(userId, oldPassword, newPassword);
            if (!success) {
                return Result.error("修改密码失败");
            }
        } else if (user.getPassword() != null) {
            // 如果请求体中包含密码，但没有提供旧密码和新密码参数，则认为是非法操作，或者忽略此字段
            // 这里的处理方式是忽略请求体中的密码字段，强制通过参数修改密码
            user.setPassword(null);
        }

        // 更新其他用户信息
        boolean success = userService.updateUser(user);
        if (!success) {
            return Result.error("更新用户信息失败");
        }

        return Result.success(userService.getUserById(userId));
    }

    @GetMapping("/check-phone")
    public boolean checkPhoneExists(@RequestParam String phone) {
        return userService.checkPhoneExists(phone);
    }

    @GetMapping("/me")
    public User getCurrentUser(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("未登录或Token无效");
        }
        return userService.getUserById(userId);
    }
}
