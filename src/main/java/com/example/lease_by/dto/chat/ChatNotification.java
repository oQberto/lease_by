package com.example.lease_by.dto.chat;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ChatNotification {
    Long senderId;
    Long recipientId;
    String content;
}
