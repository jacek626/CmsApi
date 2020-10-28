package com.app.cms.dto.converter;

import com.app.cms.api.UserApi;
import com.app.cms.dto.UserDto;
import com.app.cms.entity.User;
import com.app.cms.valueobject.user.Email;
import com.app.cms.valueobject.user.Login;
import com.app.cms.valueobject.user.Password;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserConverter implements ObjectConverter<User, UserDto> {

    private final ModelMapper modelMapper;

    private final com.fasterxml.jackson.databind.ObjectMapper jacksonModelMapper;

    public UserConverter(ModelMapper modelMapper, ObjectMapper jacksonModelMapper) {
        this.modelMapper = modelMapper;
        this.jacksonModelMapper = jacksonModelMapper;
    }

    @Override
    public UserDto toDto(User user) {
        user.setPassword(null);

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setLogin(user.getLogin().getValue());
        userDto.setEmail(user.getEmail().getValue());

        userDto.add(
                linkTo(methodOn(UserApi.class).getUserById(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserApi.class).getUsers(0, 10, null, null)).withRel("users"));

        return userDto;
    }

    public Map<String, Object> toMap(UserDto userDto) {
        return jacksonModelMapper.convertValue(userDto, Map.class);
    }

    @Override
    public User toEntity(UserDto userDto) {
        return toEntity(userDto, ConvertType.ALL_FIELDS_MUST_BE_SET);
    }


    public User toEntity(Map<String, Object> objectAsMap) {
        var userDto = jacksonModelMapper.convertValue(objectAsMap, UserDto.class);

        return toEntity(userDto, ConvertType.PART_OF_FIELDS_CAN_BE_SET);
    }

    private User toEntity(UserDto userDto, ConvertType convertType) {
        User user = modelMapper.map(userDto, User.class);

        if (userDto.getLogin() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            user.setLogin(Login.of(userDto.getLogin()));

        if (userDto.getPassword() != null || userDto.getPasswordConfirm() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            user.setPassword(Password.of(userDto.getPassword(), userDto.getPasswordConfirm()));

        if (userDto.getEmail() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            user.setEmail(Email.of(userDto.getEmail()));

        return user;
    }

}
