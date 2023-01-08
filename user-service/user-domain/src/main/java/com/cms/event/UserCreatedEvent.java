package com.cms.event;

import com.cms.agregate.User;

import java.time.LocalDateTime;


public class UserCreatedEvent extends UserEvent {

    public UserCreatedEvent(User user, LocalDateTime createdAt) {
        super(user, createdAt);
    }
}
