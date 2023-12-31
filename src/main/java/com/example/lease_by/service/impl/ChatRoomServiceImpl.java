package com.example.lease_by.service.impl;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.mapper.ChatRoomMapper;
import com.example.lease_by.model.entity.ChatRoom;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;
import com.example.lease_by.model.repository.ChatRoomRepository;
import com.example.lease_by.service.ChatRoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMapper chatRoomMapper;

    @Override
    public ChatRoomDto getChatRoomById(Long id) {
        return chatRoomRepository.findById(id)
                .map(chatRoomMapper::mapToChatRoomDto)
                .orElseThrow(() -> new EntityNotFoundException("Chat room with id: " + id + " not found!"));
    }

    @Override
    public List<ChatRoomDto> getChatRoomsBySenderId(Long senderId) {
        return chatRoomRepository.findAllBySenderId(senderId).stream()
                .map(chatRoomMapper::mapToChatRoomDto)
                .toList();
    }

    @Override
    public ChatRoomDto getChatBy(Long senderId, Long recipientId) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId, recipientId)
                .map(chatRoomMapper::mapToChatRoomDto)
                .orElseThrow(() -> new EntityNotFoundException("Chat not found!"));
    }

    @Override
    public ChatRoomDto updateChatRoomStatus(Long id, ChatRoomStatus status) {
        return chatRoomRepository.findById(id)
                .map(chatRoom -> setNewStatus(status, chatRoom))
                .map(chatRoomRepository::saveAndFlush)
                .map(chatRoomMapper::mapToChatRoomDto)
                .orElseThrow(() -> new EntityNotFoundException("Chat room with id: " + id + " not found!"));
    }

    private static ChatRoom setNewStatus(ChatRoomStatus status, ChatRoom chatRoom) {
        chatRoom.setStatus(status);
        return chatRoom;
    }
}
