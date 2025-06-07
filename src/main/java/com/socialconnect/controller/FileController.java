package com.socialconnect.controller;

import com.socialconnect.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("file") MultipartFile file,   // 从请求中获取文件
            @RequestAttribute("userId") Long userId) {  // 从请求属性中获取 userId
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("文件不能为空"));
            }

            logger.info("Received avatar upload request from user: {}", userId);
            String fileUrl = fileService.uploadAvatar(userId, file);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "上传成功");
            response.put("data", Map.of("url", fileUrl));
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (IOException e) {
            logger.error("File upload failed: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(createErrorResponse("文件上传失败：" + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error during file upload: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(createErrorResponse("服务器内部错误"));
        }
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<?> deleteAvatar(
            @RequestParam("filename") String filename,
            @RequestAttribute("userId") Long userId) {
        try {
            if (filename == null || filename.isEmpty()) {
                return ResponseEntity.badRequest().body(createErrorResponse("文件名不能为空"));
            }

            logger.info("Received avatar deletion request from user: {}", userId);
            fileService.deleteAvatar(userId, filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "删除成功");
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(createErrorResponse(e.getMessage()));
        } catch (IOException e) {
            logger.error("File deletion failed: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(createErrorResponse("文件删除失败：" + e.getMessage()));
        } catch (Exception e) {
            logger.error("Unexpected error during file deletion: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(createErrorResponse("服务器内部错误"));
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("message", message);
        return response;
    }
} 