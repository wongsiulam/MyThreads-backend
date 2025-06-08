package com.socialconnect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socialconnect.entity.Comment;
import com.socialconnect.repository.CommentMapper;
import com.socialconnect.service.CommentService;
import com.socialconnect.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostService postService; // 用于更新帖子的评论数量

    @Override
    public Comment createComment(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        commentMapper.insert(comment);

        // 更新对应帖子的评论数量
        postService.incrementCommentsCount(comment.getPostId());

        return comment;
    }

    @Override
    public Comment getCommentById(Long commentId) {
        return commentMapper.selectById(commentId);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("post_id", postId);
        queryWrapper.isNull("parent_comment_id"); // 只获取顶级评论
        queryWrapper.orderByAsc("create_time"); // 按时间正序排列
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public List<Comment> getRepliesByParentCommentId(Long parentCommentId) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_comment_id", parentCommentId);
        queryWrapper.orderByAsc("create_time");
        return commentMapper.selectList(queryWrapper);
    }

    @Override
    public boolean deleteComment(Long commentId, Long userId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            return false; // 评论不存在
        }
        if (!comment.getUserId().equals(userId)) {
            // 只有评论者才能删除自己的评论
            throw new RuntimeException("您没有权限删除此评论");
        }

        boolean deleted = commentMapper.deleteById(commentId) > 0;
        if (deleted) {
            // 更新对应帖子的评论数量
            postService.decrementCommentsCount(comment.getPostId());
        }
        return deleted;
    }
} 