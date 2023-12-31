package com.example.lease_by.service;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> getChatRoomsBySenderId(Long senderId);

    ChatRoomDto getChatBy(Long senderId, Long recipientId);

    ChatRoomDto updateChatRoomStatus(Long id, ChatRoomStatus status);
}
