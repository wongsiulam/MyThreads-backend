package com.socialconnect.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("post")
public class Post {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId; // 发布者ID
    private String content; // 帖子内容
    private String imageUrl; // 图片URL
    private String videoUrl; // 视频URL
    private Integer likesCount; // 点赞数
    private Integer commentsCount; // 评论数
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}