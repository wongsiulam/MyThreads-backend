package com.socialconnect.service;

import com.socialconnect.entity.Post;
import java.util.List;

public interface PostService {
    /**
     * 发布新帖子
     * @param post 帖子对象
     * @return 发布成功的帖子对象
     */
    Post createPost(Post post);

    /**
     * 根据ID获取帖子
     * @param postId 帖子ID
     * @return 帖子对象
     */
    Post getPostById(Long postId);

    /**
     * 获取所有帖子列表（可以根据需要添加分页和筛选参数）
     * @return 帖子列表
     */
    List<Post> getAllPosts();

    /**
     * 获取某个用户发布的帖子列表
     * @param userId 用户ID
     * @return 帖子列表
     */
    List<Post> getPostsByUserId(Long userId);

    /**
     * 更新帖子
     * @param post 帖子对象
     * @return 更新后的帖子对象
     */
    Post updatePost(Post post);

    /**
     * 删除帖子
     * @param postId 帖子ID
     * @param userId 操作用户ID，用于权限校验
     * @return 是否删除成功
     */
    boolean deletePost(Long postId, Long userId);

    /**
     * 增加帖子点赞数
     * @param postId 帖子ID
     * @return 是否成功
     */
    int incrementLikesCount(Long postId);

    /**
     * 减少帖子点赞数
     * @param postId 帖子ID
     * @return 是否成功
     */
    boolean decrementLikesCount(Long postId);

    /**
     * 增加帖子评论数
     * @param postId 帖子ID
     * @return 是否成功
     */
    boolean incrementCommentsCount(Long postId);

    /**
     * 减少帖子评论数
     * @param postId 帖子ID
     * @return 是否成功
     */
    boolean decrementCommentsCount(Long postId);
}