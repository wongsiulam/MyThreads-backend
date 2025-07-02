package com.socialconnect.service;

import java.util.List;

import com.socialconnect.entity.Message;

public interface MessageService {
    void sendMessage(Message message);
    List<Message> getMessages(Long senderId, Long receiverId);
    void deleteMessage(Long id);
    void updateMessageStatus(Long id, Integer status);
    Message getMessageById(Long id);
    List<Message> getlatestMessagesFromSenders(Long receiverId);
}
