package com.socialconnect.controller;

import com.socialconnect.entity.Message;
import com.socialconnect.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // 1. 发送消息
    @PostMapping("/send")
    public void sendMessage(@RequestBody Message message, HttpServletRequest request) {
        // 可根据principal获取当前登录用户ID，设置为senderId
        // message.setSenderId(currentUserId);
        messageService.sendMessage(message);
    }

    // 2. 获取与某用户的聊天记录（双向）
    @GetMapping("/chat")
    public List<Message> getChatMessages(@RequestParam Long userId, HttpServletRequest request) {
        // currentUserId为自己，userId为对方
        Long currentUserId = (Long) request.getAttribute("userId"); /* 从principal或token中获取 */;
        return messageService.getMessages(currentUserId, userId);
    }

    // 3. 获取最近联系人及其最新消息
    @GetMapping("/recent")
    public List<Message> getRecentMessages(HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        return messageService.getlatestMessagesFromSenders(currentUserId);
    }

    // 4. 删除消息
    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
    }

    // 5. 更新消息状态
    @PutMapping("/{id}/status")
    public void updateMessageStatus(@PathVariable Long id, @RequestParam Integer status) {
        messageService.updateMessageStatus(id, status);
    }
}
