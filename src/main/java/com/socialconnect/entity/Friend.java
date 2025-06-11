package com.socialconnect.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("friends")
public class Friend {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("friend_id")
    private Long friendId;

    @TableField("status")
    private String status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}

enum FriendStatus {
    PENDING,    // 等待接受
    ACCEPTED,   // 已接受
    REJECTED,   // 已拒绝
    BLOCKED     // 已拉黑
} 