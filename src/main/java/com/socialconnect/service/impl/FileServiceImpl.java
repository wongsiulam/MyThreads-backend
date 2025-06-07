package com.socialconnect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socialconnect.entity.User;
import com.socialconnect.repository.UserMapper;
import com.socialconnect.service.FileService;
import com.socialconnect.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private final FileUtil fileUtil;
    private final UserMapper userMapper;
    
    @Value("${file.upload.base-url}")
    private String baseUrl;

    public FileServiceImpl(FileUtil fileUtil, UserMapper userMapper) {
        this.fileUtil = fileUtil;
        this.userMapper = userMapper;
    }

    @Override
    public String uploadAvatar(Long userId, MultipartFile file) throws IOException {
        // 保存文件
        String filename = fileUtil.saveFile(file);
        
        // 构建文件URL
        String fileUrl = baseUrl + "/uploads/" + filename;
        
        // 更新用户头像URL
        User user = userMapper.selectById(userId);
        if (user != null) {
            // 如果用户已有头像，删除旧文件
            if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
                String oldFilename = user.getAvatar().substring(user.getAvatar().lastIndexOf("/") + 1);
                fileUtil.deleteFile(oldFilename);
            }
            
            user.setAvatar(fileUrl);
            userMapper.updateById(user);
        }
        
        return fileUrl;
    }

    @Override
    public void deleteAvatar(Long userId, String filename) throws IOException {
        User user = userMapper.selectById(userId);
        if (user != null && user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            fileUtil.deleteFile(filename);
            user.setAvatar(null);
            userMapper.updateById(user);
        }
    }
} 