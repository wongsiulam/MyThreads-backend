package com.socialconnect.service;

import com.socialconnect.entity.Comment;
import java.util.List;

public interface CommentService {
    /**
     * 发布新评论
     * @param comment 评论对象
     * @return 发布成功的评论对象
     */
    Comment createComment(Comment comment);

    /**
     * 根据ID获取评论
     * @param commentId 评论ID
     * @return 评论对象
     */
    Comment getCommentById(Long commentId);

    /**
     * 获取某个帖子下的所有评论（可以根据需要添加分页和筛选参数）
     * @param postId 帖子ID
     * @return 评论列表
     */
    List<Comment> getCommentsByPostId(Long postId);

    /**
     * 获取某个评论的所有子评论（用于回复功能）
     * @param parentCommentId 父评论ID
     * @return 子评论列表
     */
    List<Comment> getRepliesByParentCommentId(Long parentCommentId);

    /**
     * 删除评论
     * @param commentId 评论ID
     * @param userId 操作用户ID，用于权限校验
     * @return 是否删除成功
     */
    boolean deleteComment(Long commentId, Long userId);
} 