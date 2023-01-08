package com.cms.response;


import com.cms.agregate.*;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String login;
    private UserStatus status;
    private String email;
    private Position position;


    public UserDto(User user) {
        id = user.getId().getUserId();
        login = user.getLogin().getValue();
        status = user.getStatus();
        email = user.getEmail().getValue();
        position = user.getPosition();
    }
}
