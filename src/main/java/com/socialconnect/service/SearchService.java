package com.socialconnect.service;

import com.socialconnect.entity.Post;
import com.socialconnect.entity.User;
import java.util.List;

public interface SearchService {
    /**
     * 搜索用户
     * @param keyword 搜索关键词
     * @return 用户列表
     */
    List<User> searchUsers(String keyword);

    /**
     * 搜索帖子
     * @param keyword 搜索关键词
     * @return 帖子列表
     */
    List<Post> searchPosts(String keyword);

    /**
     * 搜索话题/标签
     * @param keyword 搜索关键词
     * @return 帖子列表（包含相关话题/标签的帖子）
     */
    List<Post> searchTopics(String keyword);
} 