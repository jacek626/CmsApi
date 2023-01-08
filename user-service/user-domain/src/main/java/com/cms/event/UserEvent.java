package com.cms.event;

import com.cms.agregate.User;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public abstract class UserEvent {

    private final User user;
    private final LocalDateTime createdAt;

}
