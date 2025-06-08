package com.socialconnect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socialconnect.entity.User;
import com.socialconnect.repository.UserMapper;
import com.socialconnect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList; // 用于构建空的权限列表
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User register(String phone, String password) {
        // 1. 检查手机号是否已注册
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", phone);
        User exist = userMapper.selectOne(query);
        if (exist != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 2. 密码加密（简单MD5，生产建议用更安全的加密方式）
        String encryptedPwd = DigestUtils.md5DigestAsHex(password.getBytes());

        // 3. 创建新用户
        User user = new User();
        user.setPhone(phone);
        user.setPassword(encryptedPwd);
        userMapper.insert(user);

        return user;
    }

    @Override
    public Boolean updateUser(User user) {
        return userMapper.updateById(user) > 0;
    }

    @Override
    public void deleteUser(Long id) {
        // TODO: 实现用户删除
        
    }
    @Override
    public User getUserById(Long id) {
        // TODO: 实现根据ID查询用户
        return userMapper.selectById(id);
    }

    @Override
    public User login(String phone, String password) {
        // 1. 查询用户
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", phone);
        User user = userMapper.selectOne(query);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 2. 校验密码（和注册时加密方式一致）
        String encryptedPwd = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!encryptedPwd.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        return user;
    }

    @Override
    public boolean checkPhoneExists(String phone) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.eq("phone", phone);
        return userMapper.selectCount(query) > 0;
    }

    @Override
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        Long userId = Long.valueOf(userIdStr); // 将字符串转换为Long
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new UsernameNotFoundException("用户ID不存在: " + userIdStr);
        }
        // 构建 Spring Security 的 UserDetails 对象
        // 第三个参数是权限列表，这里我们先给一个空的，后续可以根据用户角色添加
        return new org.springframework.security.core.userdetails.User(
                userIdStr, // 用户名（这里用用户ID的字符串形式）
                user.getPassword(), // 密码（这里是加密后的密码）
                new ArrayList<>() // 权限列表，目前为空
        );
    }


    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        User user = getUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        String encryptedOldPwd = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        System.out.println("旧密码加密后: " + encryptedOldPwd);
        System.out.println("数据库中存储的密码: " + user.getPassword());
        if (!encryptedOldPwd.equals(user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }

        // 更新新密码
        String encryptedNewPwd = DigestUtils.md5DigestAsHex(newPassword.getBytes());
        user.setPassword(encryptedNewPwd);

        return updateUser(user);
    }
}
