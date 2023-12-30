package com.example.lease_by.service.impl;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.mapper.ChatRoomMapper;
import com.example.lease_by.model.repository.ChatRoomRepository;
import com.example.lease_by.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMapper chatRoomMapper;

    @Override
    public List<ChatRoomDto> getChatRoomsBySenderId(Long senderId) {
        return chatRoomRepository.findAllBySenderId(senderId).stream()
                .map(chatRoomMapper::mapToChatRoomDto)
                .toList();
    }
}
