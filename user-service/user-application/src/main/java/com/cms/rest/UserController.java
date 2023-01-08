package com.cms.rest;

import com.cms.UserApplicationRepository;
import com.cms.UserApplicationService;
import com.cms.UserJpaRepository;
import com.cms.agregate.User;
import com.cms.command.CreateUserCommand;
import com.cms.command.EditUserCommand;
import com.cms.exception.UserNotFound;
import com.cms.response.UserDto;
import com.cms.response.UserEditedResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserApplicationService userApplicationService;
    private final UserApplicationRepository userRepository;
    private final UserJpaRepository userJpaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Create new user")
    public ResponseEntity<UserDto> create(@RequestBody CreateUserCommand createUserCommand) {
        var userDto = new UserDto(userApplicationService.create(createUserCommand));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @GetMapping("{userId}")
    @Operation(description = "Load user by id")
    public ResponseEntity<UserDto> loadById(@PathVariable("userId") String userId) {
        UserDto userDto = userRepository.findById(User.UserId.of(UUID.fromString(userId))).map(UserDto::new)
                        .orElseThrow(() -> new UserNotFound("User not found"));
        return  ResponseEntity.ok(userDto);
    }

    @GetMapping
    @Operation(description = "Load users")
    public Page<UserDto> loadUsers(@RequestParam int page,
                                   @RequestParam int size,
                                   @RequestParam(defaultValue = "id") String sortBy) {
        PageRequest pageRequest = PageRequest.of(page,size, Sort.by(sortBy));
        return userJpaRepository.findAll(pageRequest).map(UserDto::new);
    }


    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserEditedResponse edit(@RequestBody @Valid EditUserCommand editUserCommand) {

        return userApplicationService.edit(editUserCommand);
    }


/*    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable @NotNull UUID userId) {

        userApplicationService.deactivate(userId);
    }

    @DeleteMapping(path = "/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull UUID userId) {

        userApplicationService.delete(userId);
    }*/
}
