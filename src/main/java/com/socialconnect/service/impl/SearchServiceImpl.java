package com.socialconnect.service.impl;

import com.socialconnect.entity.Post;
import com.socialconnect.entity.User;
import com.socialconnect.repository.PostMapper;
import com.socialconnect.repository.UserMapper;
import com.socialconnect.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Override
    public List<User> searchUsers(String keyword) {
        return userMapper.searchUsers(keyword);
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return postMapper.searchPosts(keyword);
    }

    @Override
    public List<Post> searchTopics(String keyword) {
        return postMapper.searchTopics(keyword);
    }
} 