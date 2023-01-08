package com.cms;


import com.cms.agregate.User;
import com.cms.avro.model.UserEventAvro;
import com.cms.command.CreateUserCommand;
import com.cms.command.EditUserCommand;
import com.cms.exception.UserNotExistsError;
import com.cms.exception.UserNotFound;
import com.cms.exception.UserWithSameLoginExistsError;
import com.cms.messaging.publisher.UserEventKafkaPublisher;
import com.cms.response.UserDto;
import com.cms.response.UserEditedResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

//@Service
@Service
@AllArgsConstructor
public class UserApplicationService {

    private final UserRepository userRepository;

   // private final UserEventProducer producer;
    private final UserEventKafkaPublisher producer;

    private final UserDomainService userDomainService = new UserDomainService();

 //   private final KafkaAvroProperties kafkaAvroProperties;

    public User create(CreateUserCommand createUserCommand) {
        var user = createUserCommand.toAggregate();

        List<User> userWithSameLogin = userRepository.findByLogin(user.getLogin());
        if (userWithSameLogin.isEmpty()) {
                userRepository.save(user);
        } else {
                throw new UserWithSameLoginExistsError("User with the same login exists");
            }


        var avroEvent = new UserEventAvro();
        avroEvent.setId(user.getId().getUserId().toString());
        avroEvent.setFirstName("Jan");
        avroEvent.setLastName("Kowalski");
        producer.publish(user.getId().getUserId().toString(), avroEvent);

        return user;
    }

    public void emailActivate(User.UserId userId) {
        userRepository.findById(userId).ifPresentOrElse(user -> {
            user.activate();
            userRepository.save(user);
        }, () -> {
            throw new UserNotExistsError("User with id " + userId + " not found");
        } );

    }

    public ResponseEntity<UserDto> loadById(@PathVariable @NotNull User.UserId userId) {
        UserDto userDto =
                userRepository
                        .findById(userId)
                     //   .findById(User.UserId.of(userId))
                        .map(UserDto::new)
                        .orElseThrow(() -> new UserNotFound("User not found"));
        return  ResponseEntity.ok(userDto);
    }

    public void loadUserDetails(String login) {
        //    User user = userRepository.findByLogin(Login.of(login));
        //    userService.registerUser(user);
    }

    public void deactivate(UUID userId) {
    }

    public void delete(UUID userId) {
    }

    public UserEditedResponse edit(EditUserCommand editUserCommand) {

        return new UserEditedResponse();
    }
}
