package com.example.lease_by.listener.event;

import com.example.lease_by.dto.account.UserReadDto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = true)
public class RegistrationCompleteEvent extends ApplicationEvent {
    private final UserReadDto userReadDto;

    public RegistrationCompleteEvent(UserReadDto userReadDto) {
        super(userReadDto);
        this.userReadDto = userReadDto;
    }
}
