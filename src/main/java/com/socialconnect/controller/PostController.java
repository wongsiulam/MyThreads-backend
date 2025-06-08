package com.socialconnect.controller;

import com.socialconnect.entity.Post;
import com.socialconnect.entity.Comment;
import com.socialconnect.service.PostService;
import com.socialconnect.service.CommentService;
import com.socialconnect.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    /**
     * 发布新帖子
     */
    @PostMapping
    public Result<Post> createPost(@RequestBody Post post, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录或Token无效");
        }
        post.setUserId(userId);
        Post createdPost = postService.createPost(post);
        return Result.success(createdPost);
    }

    /**
     * 获取所有帖子列表
     */
    @GetMapping
    public Result<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return Result.success(posts);
    }

    /**
     * 根据ID获取帖子
     */
    @GetMapping("/{postId}")
    public Result<Post> getPostById(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);
        if (post == null) {
            return Result.error("帖子不存在");
        }
        return Result.success(post);
    }

    /**
     * 获取某个用户发布的帖子列表
     */
    @GetMapping("/user/{userId}")
    public Result<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return Result.success(posts);
    }

    /**
     * 更新帖子
     */
    @PutMapping("/{postId}")
    public Result<Post> updatePost(@PathVariable Long postId, @RequestBody Post post, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        if (currentUserId == null) {
            return Result.error("未登录或Token无效");
        }

        Post existingPost = postService.getPostById(postId);
        if (existingPost == null) {
            return Result.error("帖子不存在");
        }
        if (!existingPost.getUserId().equals(currentUserId)) {
            return Result.error("您没有权限修改此帖子");
        }

        post.setId(postId);
        post.setUserId(currentUserId); // 确保用户ID不变
        Post updatedPost = postService.updatePost(post);
        return Result.success(updatedPost);
    }

    /**
     * 删除帖子
     */
    @DeleteMapping("/{postId}")
    public Result<Void> deletePost(@PathVariable Long postId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录或Token无效");
        }
        boolean success = postService.deletePost(postId, userId);
        if (!success) {
            return Result.error("删除帖子失败或无权限");
        }
        return Result.success();
    }

    /**
     * 点赞帖子
     */
    @PostMapping("/{postId}/like")
    public Result<Void> likePost(@PathVariable Long postId) {
        boolean success = postService.incrementLikesCount(postId);
        if (!success) {
            return Result.error("点赞失败");
        }
        return Result.success();
    }

    /**
     * 取消点赞帖子
     */
    @DeleteMapping("/{postId}/like")
    public Result<Void> unlikePost(@PathVariable Long postId) {
        boolean success = postService.decrementLikesCount(postId);
        if (!success) {
            return Result.error("取消点赞失败");
        }
        return Result.success();
    }

    /**
     * 发布评论
     */
    @PostMapping("/{postId}/comments")
    public Result<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录或Token无效");
        }
        comment.setPostId(postId);
        comment.setUserId(userId);
        Comment createdComment = commentService.createComment(comment);
        return Result.success(createdComment);
    }

    /**
     * 获取帖子下的所有评论
     */
    @GetMapping("/{postId}/comments")
    public Result<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return Result.success(comments);
    }

    /**
     * 获取某个评论的回复
     */
    @GetMapping("/{postId}/comments/{commentId}/replies")
    public Result<List<Comment>> getRepliesByParentCommentId(@PathVariable Long postId, @PathVariable Long commentId) {
        // 理论上这里postId可以不用，因为回复是针对特定评论的，但为了保持URL结构一致性可以保留
        List<Comment> replies = commentService.getRepliesByParentCommentId(commentId);
        return Result.success(replies);
    }

    /**
     * 删除评论
     */
    @DeleteMapping("/{postId}/comments/{commentId}")
    public Result<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return Result.error("未登录或Token无效");
        }
        boolean success = commentService.deleteComment(commentId, userId);
        if (!success) {
            return Result.error("删除评论失败或无权限");
        }
        return Result.success();
    }
}