package com.app.cms.validator;

import com.app.cms.entity.User;
import com.app.cms.error.type.LoginIsInUseException;
import com.app.cms.error.type.ObjectHaveReferencedObjects;
import com.app.cms.repository.ArticleRepository;
import com.app.cms.repository.UserRepository;
import com.app.cms.valueobject.user.Email;
import com.app.cms.valueobject.user.Login;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private UserValidator userValidator;

    @Test
    public void shouldThrowError_WhenCreateNewUser_UserWithSameLoginExists() {
        //given
        var user = User.builder().login(Login.of("login")).build();
        given(userRepository.existsByLogin(any(Login.class))).willReturn(true);

        //when, then
        assertThatThrownBy(() -> {
            userValidator.validateOnSave(user);
        }).isInstanceOf(LoginIsInUseException.class).hasMessageContaining("User login is already in use");
    }

    @Test
    public void shouldThrowError_WhenUpdateUser_UserWithSameLoginExists() {
        //given
        var user = User.builder().id(-1L).login(Login.of("login")).build();
        given(userRepository.existsByLoginAndIdNot(any(Login.class), any(Long.class))).willReturn(true);

        //when, then
        assertThatThrownBy(() -> {
            userValidator.validateOnSave(user);
        }).isInstanceOf(LoginIsInUseException.class).hasMessageContaining("User login is already in use");
    }

    @Test
    public void shouldValidateUserCorrect_WhenCreateNewUser() {
        //given
        given(articleRepository.existsByCategoryId(any(Long.class))).willReturn(false);

        //when, then
        userValidator.validateOnDelete(-1L);
    }

    @Test
    public void shouldValidateUserCorrect_WhenUpdateUser() {
        //given
        given(userRepository.existsByLoginAndIdNot(any(Login.class), any(Long.class))).willReturn(false);

        //when, then
        userValidator.validateOnDelete(-1L);
    }

    @Test
    public void shouldThrowError_WhenDeleteUser_UserHaveArticles() {
        //given
        given(articleRepository.existsByUserId(any(Long.class))).willReturn(true);

        //when, then
        assertThatThrownBy(() -> {
            userValidator.validateOnDelete(-1L);
        }).isInstanceOf(ObjectHaveReferencedObjects.class).hasMessageContaining("User has articles, delete them first");
    }

    @Test
    public void shouldValidateUserDeleteCorrect() {
        //given
        given(articleRepository.existsByCategoryId(any(Long.class))).willReturn(false);

        //when, then
        userValidator.validateOnDelete(-1L);
    }

    @Test
    public void shouldValidatePartiallyUpdatedUser() {
        //given
        var user = User.builder().id(-1L).email(Email.of("email@email.com")).build();
        //when then
        userValidator.validateOnSave(user);
    }
}
