package com.cms;

import com.cms.agregate.User;
import com.cms.event.UserCreatedEvent;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class UserDomainService  {


    public UserCreatedEvent create(User user) {
        var userCreatedEvent = new UserCreatedEvent(user, LocalDateTime.now());

        return userCreatedEvent;
    }

/*    public void emailActivate(User.UserId userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            user.activate();
            userRepository.save(user);
        }, () -> {
            throw new UserNotExistsError("User with id " + userId + " not found");
        } );

    }*/


    public void deleteUser(User.UserId userId) {

    }
}
