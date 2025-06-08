package com.socialconnect.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long postId; // 所属帖子ID
    private Long userId; // 评论者ID
    private String content; // 评论内容
    private Long parentCommentId; // 父评论ID，用于支持回复功能，如果为null则为顶级评论
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 