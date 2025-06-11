package com.socialconnect.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// FileUploadConfig.java
// 文件上传配置类，负责：
// 配置文件上传的大小限制
// 配置文件上传的临时目录
// 配置文件上传的存储路径
// 配置文件上传的允许类型

@Configuration
@ConfigurationProperties(prefix = "file.upload")
public class FileUploadConfig {
    private String uploadDir;
    private long maxSize;
    private String allowedTypes;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public String getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(String allowedTypes) {
        this.allowedTypes = allowedTypes;
    }
} 