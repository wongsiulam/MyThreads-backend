package com.socialconnect.util;

import com.socialconnect.config.FileUploadConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Component
public class FileUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    private final FileUploadConfig fileUploadConfig;

    public FileUtil(FileUploadConfig fileUploadConfig) {
        this.fileUploadConfig = fileUploadConfig;
        // 确保上传目录存在
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        try {
            Path uploadPath = Paths.get(fileUploadConfig.getUploadDir());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                logger.info("Created upload directory: {}", uploadPath);
            }
        } catch (IOException e) {
            logger.error("Failed to create upload directory: {}", e.getMessage());
            throw new RuntimeException("Failed to create upload directory", e);
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            logger.error("File is null or empty");
            throw new IllegalArgumentException("文件不能为空");
        }

        // 验证文件类型
        String originalFilename = file.getOriginalFilename();
        logger.info("Processing file: {}", originalFilename);
        
        String fileExtension = getFileExtension(originalFilename);
        if (!isAllowedFileType(fileExtension)) {
            logger.error("Invalid file type: {}", fileExtension);
            throw new IllegalArgumentException("不支持的文件类型: " + fileExtension);
        }

        // 验证文件大小
        if (file.getSize() > fileUploadConfig.getMaxSize()) {
            logger.error("File size exceeds limit: {} bytes", file.getSize());
            throw new IllegalArgumentException("文件大小超过限制");
        }

        // 生成新的文件名
        String newFilename = generateUniqueFilename(fileExtension);
        logger.info("Generated new filename: {}", newFilename);

        // 确保上传目录存在
        Path uploadPath = Paths.get(fileUploadConfig.getUploadDir());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            logger.info("Created upload directory: {}", uploadPath);
        }

        // 保存文件
        Path filePath = uploadPath.resolve(newFilename);
        try {
            Files.copy(file.getInputStream(), filePath);
            logger.info("File saved successfully: {}", filePath);
        } catch (IOException e) {
            logger.error("Failed to save file: {}", e.getMessage());
            throw new IOException("文件保存失败: " + e.getMessage());
        }

        return newFilename;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    private boolean isAllowedFileType(String fileExtension) {
        String[] allowedTypes = fileUploadConfig.getAllowedTypes().split(",");
        return Arrays.asList(allowedTypes).contains(fileExtension.toLowerCase());
    }

    private String generateUniqueFilename(String fileExtension) {
        return UUID.randomUUID().toString() + "." + fileExtension;
    }

    public void deleteFile(String filename) throws IOException {
        if (filename == null || filename.isEmpty()) {
            logger.error("Filename is null or empty");
            throw new IllegalArgumentException("文件名不能为空");
        }

        Path filePath = Paths.get(fileUploadConfig.getUploadDir(), filename);
        try {
            boolean deleted = Files.deleteIfExists(filePath);
            if (deleted) {
                logger.info("File deleted successfully: {}", filePath);
            } else {
                logger.warn("File not found for deletion: {}", filePath);
            }
        } catch (IOException e) {
            logger.error("Failed to delete file: {}", e.getMessage());
            throw new IOException("文件删除失败: " + e.getMessage());
        }
    }
} 