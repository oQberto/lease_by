package com.example.lease_by.service.impl;

import com.example.lease_by.dto.chat.ChatMessageDto;
import com.example.lease_by.mapper.ChatMessageMapper;
import com.example.lease_by.model.repository.ChatMessageRepository;
import com.example.lease_by.service.ChatMessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    @Transactional
    public ChatMessageDto saveMassage(ChatMessageDto chatMessageDto) {
        return Optional.of(
                chatMessageRepository.saveAndFlush(
                        chatMessageMapper.mapToChatMessage(chatMessageDto)
                )
        )
                .map(chatMessageMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't create a chat!"));
    }
}
