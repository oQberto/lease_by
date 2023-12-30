package com.example.lease_by.service;

import com.example.lease_by.dto.chat.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {

    List<ChatRoomDto> getChatRoomsBySenderId(Long senderId);
}
