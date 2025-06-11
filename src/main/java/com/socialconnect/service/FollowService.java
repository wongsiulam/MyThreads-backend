package com.socialconnect.service;

import com.socialconnect.entity.Follow;
import com.socialconnect.repository.FollowMapper;
import com.socialconnect.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FollowService {
    @Autowired
    private FollowMapper followRepository;
    
    @Autowired
    private UserMapper userRepository;

    @Transactional
    public Follow followUser(Long followerId, Long followingId) {
        if (!userRepository.existsById(followerId) || !userRepository.existsById(followingId)) {
            throw new RuntimeException("User not found");
        }

        if (followRepository.existsByFollowerAndFollowing(followerId, followingId)) {
            throw new RuntimeException("Already following this user");
        }

        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        
        followRepository.insert(follow);
        return follow;
    }

    @Transactional
    public void unfollowUser(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerAndFollowing(followerId, followingId);
        if (follow != null) {
            followRepository.deleteById(follow.getId());
        }
    }

    public List<Follow> getFollowers(Long userId) {
        return followRepository.findByFollowing(userId);
    }

    public List<Follow> getFollowing(Long userId) {
        return followRepository.findByFollower(userId);
    }
} 