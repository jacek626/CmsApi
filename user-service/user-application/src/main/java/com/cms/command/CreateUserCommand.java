package com.cms.command;


import com.cms.agregate.Email;
import com.cms.agregate.Login;
import com.cms.agregate.Password;
import com.cms.agregate.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateUserCommand {
    @NotNull
    private String login;
    @NotNull
    private char[] password;
    @NotNull
    private char[] passwordConfirm;
    @NotNull
    private String email;

    public User toAggregate() {
        return User.of(Login.of(login),
                Password.of(password, passwordConfirm),
                Email.of(email));
    }
}
