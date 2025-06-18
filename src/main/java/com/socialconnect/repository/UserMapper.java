package com.socialconnect.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialconnect.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 这里可以自定义更多数据库操作方法，目前继承BaseMapper即可
    
    @Select("SELECT COUNT(*) FROM user WHERE id = #{id}")
    boolean existsById(Long id);

    @Select("SELECT * FROM user WHERE phone LIKE CONCAT('%', #{keyword}, '%') OR nickname LIKE CONCAT('%', #{keyword}, '%')")
    List<User> searchUsers(String keyword);
}
