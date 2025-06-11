package com.socialconnect.controller;

import com.socialconnect.entity.Friend;
import com.socialconnect.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    @Autowired
    private FriendService friendService;

    @PostMapping("/request/{userId}/{friendId}")
    public ResponseEntity<Friend> sendFriendRequest(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        return ResponseEntity.ok(friendService.sendFriendRequest(userId, friendId));
    }

    @PutMapping("/accept/{requestId}")
    public ResponseEntity<Friend> acceptFriendRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(friendService.acceptFriendRequest(requestId));
    }

    @PutMapping("/reject/{requestId}")
    public ResponseEntity<Friend> rejectFriendRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(friendService.rejectFriendRequest(requestId));
    }

    @DeleteMapping("/{userId}/{friendId}")
    public ResponseEntity<Void> deleteFriend(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        friendService.deleteFriend(userId, friendId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/requests/{userId}")
    public ResponseEntity<List<Friend>> getFriendRequests(@PathVariable Long userId) {
        return ResponseEntity.ok(friendService.getFriendRequests(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Friend>> getFriends(@PathVariable Long userId) {
        return ResponseEntity.ok(friendService.getFriends(userId));
    }
} 