package com.example.lease_by.service;

import com.example.lease_by.dto.chat.ChatRoomDto;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    List<ChatRoomDto> getChatRoomsBySenderId(Long senderId);

    Optional<ChatRoomDto> getChatBy(Long senderId, Long recipientId);
}
