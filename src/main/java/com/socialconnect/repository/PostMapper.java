package com.socialconnect.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialconnect.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapper<Post> {
    // 可以在这里添加自定义的数据库操作方法，如果需要的话

    @Select("SELECT * FROM post WHERE content LIKE CONCAT('%', #{keyword}, '%')")
    List<Post> searchPosts(String keyword);

    @Select("SELECT * FROM post WHERE tags LIKE CONCAT('%', #{keyword}, '%')")
    List<Post> searchTopics(String keyword);
}