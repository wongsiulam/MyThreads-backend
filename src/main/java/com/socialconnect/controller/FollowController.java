package com.socialconnect.controller;

import com.socialconnect.entity.Follow;
import com.socialconnect.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    @Autowired
    private FollowService followService;

    @PostMapping("/{followerId}/{followingId}")
    public ResponseEntity<Follow> followUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        return ResponseEntity.ok(followService.followUser(followerId, followingId));
    }

    @DeleteMapping("/{followerId}/{followingId}")
    public ResponseEntity<Void> unfollowUser(
            @PathVariable Long followerId,
            @PathVariable Long followingId) {
        followService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<Follow>> getFollowers(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<Follow>> getFollowing(@PathVariable Long userId) {
        return ResponseEntity.ok(followService.getFollowing(userId));
    }
} 