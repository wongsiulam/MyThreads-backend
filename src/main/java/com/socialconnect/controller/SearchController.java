package com.socialconnect.controller;

import com.socialconnect.entity.Post;
import com.socialconnect.entity.User;
import com.socialconnect.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchUsers(keyword));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchPosts(keyword));
    }

    @GetMapping("/topics")
    public ResponseEntity<List<Post>> searchTopics(@RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchTopics(keyword));
    }
} 