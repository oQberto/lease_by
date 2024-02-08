package com.example.lease_by.mapper;

import com.example.lease_by.dto.chat.ChatMessageDto;
import com.example.lease_by.model.entity.ChatMessage;
import com.example.lease_by.model.entity.ChatRoom;
import com.example.lease_by.model.entity.User;
import com.example.lease_by.model.repository.ChatRoomRepository;
import com.example.lease_by.model.repository.UserRepository;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Component
@Mapper(componentModel = SPRING)
public abstract class ChatMessageMapper {

    @Autowired
    protected ChatRoomRepository chatRoomRepository;

    @Autowired
    protected UserRepository userRepository;

    @Mappings({
            @Mapping(target = "recipientId", expression = "java(chatMessage.getRecipient().getId())"),
            @Mapping(target = "senderId", expression = "java(chatMessage.getSender().getId())"),
            @Mapping(target = "chatRoomId", expression = "java(chatMessage.getChatRoom().getId())")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
    public abstract ChatMessageDto mapToDto(ChatMessage chatMessage);

    @Mappings({
            @Mapping(target = "recipient", expression = "java(getUserById(chatMessageDto.getRecipientId()))"),
            @Mapping(target = "sender", expression = "java(getUserById(chatMessageDto.getSenderId()))"),
            @Mapping(target = "chatRoom", expression = "java(getChatRoomById(chatMessageDto.getChatRoomId()))")
    })
    public abstract ChatMessage mapToChatMessage(ChatMessageDto chatMessageDto);

    protected User getUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow();
    }

    protected ChatRoom getChatRoomById(Long id) {
        return chatRoomRepository.findById(id)
                .orElseThrow();
    }
}
