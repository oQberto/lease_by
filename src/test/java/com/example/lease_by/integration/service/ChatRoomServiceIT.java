package com.example.lease_by.integration.service;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.service.ChatRoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
public class ChatRoomServiceIT extends IntegrationTestBase {
    private static final Long EXISTING_SENDER_ID = 1L;
    private static final Long EXISTING_RECIPIENT_ID = 2L;
    private static final Long NOT_EXISTING_USER_ID = Long.MAX_VALUE;

    private final ChatRoomService chatRoomService;

    @Test
    public void getChatRoomsBySenderId_whenSenderIdExists_shouldReturnListOfChats() {
        List<ChatRoomDto> actualResult = chatRoomService.getChatRoomsBySenderId(EXISTING_SENDER_ID);
        assertThat(actualResult).hasSize(1);

        List<Long> recipientIds = actualResult
                .stream()
                .map(ChatRoomDto::getRecipientId)
                .toList();
        assertThat(recipientIds).contains(2L);
    }

    @Test
    public void getChatBySenderAndRecipientIds_whenSenderAndRecipientIdsExist_shouldThrowEntityNotFoundException() {
        Optional<ChatRoomDto> actualResult = Optional.of(chatRoomService.getChatBy(EXISTING_SENDER_ID, EXISTING_RECIPIENT_ID));

        assertThat(actualResult).isPresent();
    }

    @Test
    public void getChatBySenderAndRecipientIds_whenSenderAndRecipientIdsDoNotExist_shouldThrowEntityNotFoundException() {
        assertThatThrownBy(() -> chatRoomService.getChatBy(NOT_EXISTING_USER_ID, NOT_EXISTING_USER_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Chat not found!");
    }
}
