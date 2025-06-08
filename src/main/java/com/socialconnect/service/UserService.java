package com.socialconnect.service;

import com.socialconnect.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    /**
     * 用户注册
     * @param phone 手机号
     * @param password 密码
     * @return 注册成功的用户对象
     */
    User register(String phone, String password);
    /**
     * 用户登录
     * @param phone 手机号
     * @param password 密码
     * @return 登录成功的用户对象
     */
    User login(String phone, String password);
    /**
     * 根据ID获取用户
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 更新后的用户对象
     */
    Boolean updateUser(User user);
    /**
     * 删除用户
     * @param id 用户ID
     */
    void deleteUser(Long id);
    /**
     * 检查手机号是否存在
     * @param phone 手机号
     * @return 是否存在
     */
    boolean checkPhoneExists(String phone);

    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否修改成功
     */
    boolean changePassword(Long userId, String oldPassword, String newPassword);
}
