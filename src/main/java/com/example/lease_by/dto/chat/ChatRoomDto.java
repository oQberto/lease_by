package com.example.lease_by.dto.chat;

import com.example.lease_by.model.entity.ChatMessage;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ChatRoomDto {
    Long id;
    Long senderId;
    Long recipientId;
    ChatRoomStatus status;

    @Builder.Default
    List<ChatMessage> messages = new ArrayList<>();
}
