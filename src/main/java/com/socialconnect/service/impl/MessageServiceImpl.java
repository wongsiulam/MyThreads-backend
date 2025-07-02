package com.socialconnect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.socialconnect.entity.Message;
import com.socialconnect.repository.MessageMapper;
import com.socialconnect.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    // 发送消息
    @Override
    public void sendMessage(Message message) {
        messageMapper.insert(message);
    }

    // 获取消息 一对一
    @Override
    public List<Message> getMessages(Long senderId, Long receiverId){
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        // 双向的信息查询 A->B 和 B->A
        queryWrapper.and(wrapper -> 
            wrapper
                .eq("sender_id", senderId).eq("receiver_id", receiverId)
                .or()
                .eq("sender_id", receiverId).eq("receiver_id", senderId)
        );
        return messageMapper.selectList(queryWrapper);
    }

    // 删除消息
    @Override
    public void deleteMessage(Long id){
        messageMapper.deleteById(id);
    }

    // 更新消息状态
    @Override
    public void updateMessageStatus(Long id, Integer status){
        Message message = messageMapper.selectById(id);
        if (message != null) {
            message.setStatus(status);
            messageMapper.updateById(message);
        }
    }

    // 获取消息单条信息
    @Override
    public Message getMessageById(Long id){
        return messageMapper.selectById(id);
    }

    // 获取消息 一对多
    @Override
    public List<Message> getlatestMessagesFromSenders(Long receiverId){
        return messageMapper.selectLatestMessagesFromSenders(receiverId);
    }
}
