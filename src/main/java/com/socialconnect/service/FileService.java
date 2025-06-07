package com.socialconnect.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadAvatar(Long userId, MultipartFile file) throws IOException;
    void deleteAvatar(Long userId, String filename) throws IOException;
} 