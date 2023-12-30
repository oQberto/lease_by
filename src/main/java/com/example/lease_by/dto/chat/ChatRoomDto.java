package com.example.lease_by.dto.chat;

import com.example.lease_by.model.entity.ChatMessage;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ChatRoomDto {
    Long senderId;
    Long recipientId;

    @Builder.Default
    List<ChatMessage> messages = new ArrayList<>();
}
