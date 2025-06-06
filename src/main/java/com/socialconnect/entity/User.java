package com.socialconnect.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;

import java.time.LocalDateTime;

// @Data：Lombok注解，自动生成getter/setter等方法。
// @TableName("user")：指定实体类对应的数据库表名。
// @TableId(type = IdType.AUTO)：主键自增。
// 字段名与表结构一一对应。

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String phone;
    private String password;
    private String nickname;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}