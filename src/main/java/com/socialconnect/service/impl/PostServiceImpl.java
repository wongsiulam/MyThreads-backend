package com.socialconnect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socialconnect.entity.Post;
import com.socialconnect.repository.PostMapper;
import com.socialconnect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Override
    public Post createPost(Post post) {
        post.setCreateTime(LocalDateTime.now());
        post.setUpdateTime(LocalDateTime.now());
        post.setLikesCount(0);
        post.setCommentsCount(0);
        postMapper.insert(post);
        return post;
    }

    @Override
    public Post getPostById(Long postId) {
        return postMapper.selectById(postId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postMapper.selectList(null);
    }

    @Override
    public List<Post> getPostsByUserId(Long userId) {
        QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time"); // 按时间倒序排列
        return postMapper.selectList(queryWrapper);
    }

    @Override
    public Post updatePost(Post post) {
        post.setUpdateTime(LocalDateTime.now());
        postMapper.updateById(post);
        return post;
    }

    @Override
    public boolean deletePost(Long postId, Long userId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false; // 帖子不存在
        }
        if (!post.getUserId().equals(userId)) {
            // 只有帖子发布者才能删除自己的帖子
            throw new RuntimeException("您没有权限删除此帖子");
        }
        return postMapper.deleteById(postId) > 0;
    }

    @Override
    public boolean incrementLikesCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        post.setLikesCount(post.getLikesCount() + 1);
        return postMapper.updateById(post) > 0;
    }

    @Override
    public boolean decrementLikesCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        if (post.getLikesCount() > 0) {
            post.setLikesCount(post.getLikesCount() - 1);
        }
        return postMapper.updateById(post) > 0;
    }

    @Override
    public boolean incrementCommentsCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        post.setCommentsCount(post.getCommentsCount() + 1);
        return postMapper.updateById(post) > 0;
    }

    @Override
    public boolean decrementCommentsCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            return false;
        }
        if (post.getCommentsCount() > 0) {
            post.setCommentsCount(post.getCommentsCount() - 1);
        }
        return postMapper.updateById(post) > 0;
    }
}