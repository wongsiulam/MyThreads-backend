package com.socialconnect.service;

import com.socialconnect.entity.Friend;
import com.socialconnect.repository.FriendMapper;
import com.socialconnect.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendMapper friendRepository;
    
    @Autowired
    private UserMapper userRepository;

    @Transactional
    public Friend sendFriendRequest(Long userId, Long friendId) {
        if (!userRepository.existsById(userId) || !userRepository.existsById(friendId)) {
            throw new RuntimeException("User not found");
        }

        if (friendRepository.existsByUserAndFriend(userId, friendId)) {
            throw new RuntimeException("Friend request already exists");
        }

        Friend friendRequest = new Friend();
        friendRequest.setUserId(userId);
        friendRequest.setFriendId(friendId);
        friendRequest.setStatus("PENDING");
        
        friendRepository.insert(friendRequest);
        return friendRequest;
    }

    @Transactional
    public Friend acceptFriendRequest(Long requestId) {
        Friend friendRequest = friendRepository.selectById(requestId);
        if (friendRequest == null) {
            throw new RuntimeException("Friend request not found");
        }
        
        friendRequest.setStatus("ACCEPTED");
        friendRepository.updateById(friendRequest);
        return friendRequest;
    }

    @Transactional
    public Friend rejectFriendRequest(Long requestId) {
        Friend friendRequest = friendRepository.selectById(requestId);
        if (friendRequest == null) {
            throw new RuntimeException("Friend request not found");
        }
        
        friendRequest.setStatus("REJECTED");
        friendRepository.updateById(friendRequest);
        return friendRequest;
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId) {
        Friend friendRelation = friendRepository.findByUserAndFriend(userId, friendId);
        if (friendRelation != null) {
            friendRepository.deleteById(friendRelation.getId());
        }
    }

    public List<Friend> getFriendRequests(Long userId) {
        return friendRepository.findByFriendAndStatus(userId, "PENDING");
    }

    public List<Friend> getFriends(Long userId) {
        return friendRepository.findByUserAndStatus(userId, "ACCEPTED");
    }
} 