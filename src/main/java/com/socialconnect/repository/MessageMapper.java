package com.socialconnect.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.socialconnect.entity.Message;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    // 可以在这里添加自定义的数据库操作方法，如果需要的话
    @Select("SELECT * FROM message m " +
            "WHERE m.receiver_id = #{receiverId} " +
            "AND m.id IN ( " +
            "    SELECT MAX(id) FROM message WHERE receiver_id = #{receiverId} GROUP BY sender_id " +
            ") " +
            "ORDER BY m.timestamp DESC")
    List<Message> selectLatestMessagesFromSenders(@Param("receiverId") Long receiverId);
}
