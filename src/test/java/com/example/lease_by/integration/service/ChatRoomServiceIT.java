package com.example.lease_by.integration.service;

import com.example.lease_by.dto.chat.ChatRoomDto;
import com.example.lease_by.integration.IntegrationTestBase;
import com.example.lease_by.mapper.ChatRoomMapper;
import com.example.lease_by.model.entity.ChatRoom;
import com.example.lease_by.model.entity.enums.ChatRoomStatus;
import com.example.lease_by.model.repository.ChatRoomRepository;
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
    private static final Long EXISTING_CHAT_ROOM_ID = 1L;
    private static final Long NOT_EXISTED_ID = Long.MAX_VALUE;

    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    private final ChatRoomMapper chatRoomMapper;

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
        assertThatThrownBy(() -> chatRoomService.getChatBy(NOT_EXISTED_ID, NOT_EXISTED_ID))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Chat not found!");
    }

    @Test
    public void updateChatRoomStatus_whenChatRoomExists_shouldReturnUpdatedChatRoom() {
        Optional<ChatRoom> existingChatRoom = chatRoomRepository.findById(EXISTING_CHAT_ROOM_ID);
        assertThat(existingChatRoom).isPresent();
        existingChatRoom.get().setStatus(ChatRoomStatus.DELETED);

        ChatRoomDto actualResult = chatRoomService.updateChatRoomStatus(EXISTING_CHAT_ROOM_ID, ChatRoomStatus.DELETED);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult).isEqualTo(chatRoomMapper.mapToChatRoomDto(existingChatRoom.get()));
    }
}
