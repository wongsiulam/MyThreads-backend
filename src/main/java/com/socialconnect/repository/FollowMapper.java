package com.socialconnect.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialconnect.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FollowMapper extends BaseMapper<Follow> {
    @Select("SELECT * FROM follows WHERE follower_id = #{followerId}")
    List<Follow> findByFollower(Long followerId);

    @Select("SELECT * FROM follows WHERE following_id = #{followingId}")
    List<Follow> findByFollowing(Long followingId);

    @Select("SELECT * FROM follows WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    Follow findByFollowerAndFollowing(Long followerId, Long followingId);

    @Select("SELECT COUNT(*) FROM follows WHERE follower_id = #{followerId} AND following_id = #{followingId}")
    boolean existsByFollowerAndFollowing(Long followerId, Long followingId);
} 