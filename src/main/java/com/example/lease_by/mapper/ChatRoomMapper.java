package com.example.lease_by.mapper;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.model.entity.ChatRoom;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Component
@Mapper(componentModel = SPRING)
public interface ChatRoomMapper {

    @Mappings({
            @Mapping(target = "senderId", expression = "java(chatRoom.getSender().getId())"),
            @Mapping(target = "recipientId", expression = "java(chatRoom.getRecipient().getId())")
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChatRoomDto mapToChatRoomDto(ChatRoom chatRoom);
}
