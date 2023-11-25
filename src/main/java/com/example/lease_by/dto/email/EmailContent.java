package com.example.lease_by.dto.email;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailContent {

    String senderName;
    String senderEmail;
    String phoneNumber;

    String receiverEmail;
    String receiverName;

    String message;
}
