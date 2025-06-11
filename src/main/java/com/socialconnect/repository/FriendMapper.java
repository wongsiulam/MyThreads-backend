package com.socialconnect.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialconnect.entity.Friend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FriendMapper extends BaseMapper<Friend> {
    @Select("SELECT * FROM friends WHERE user_id = #{userId} AND status = #{status}")
    List<Friend> findByUserAndStatus(Long userId, String status);

    @Select("SELECT * FROM friends WHERE friend_id = #{friendId} AND status = #{status}")
    List<Friend> findByFriendAndStatus(Long friendId, String status);

    @Select("SELECT * FROM friends WHERE user_id = #{userId} AND friend_id = #{friendId}")
    Friend findByUserAndFriend(Long userId, Long friendId);

    @Select("SELECT COUNT(*) FROM friends WHERE user_id = #{userId} AND friend_id = #{friendId}")
    boolean existsByUserAndFriend(Long userId, Long friendId);
} 