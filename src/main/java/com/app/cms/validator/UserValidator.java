package com.app.cms.validator;

import com.app.cms.entity.User;
import com.app.cms.error.type.LoginIsInUseException;
import com.app.cms.error.type.ObjectHaveReferencedObjects;
import com.app.cms.repository.ArticleRepository;
import com.app.cms.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements ValidatorOnSave<User>, ValidatorOnDelete {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public UserValidator(UserRepository userRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public void validateOnSave(User user) {
        if (user.getId() == null) {
            validationOnCreation(user);
        } else {
            validationOnUpdate(user);
        }
    }

    @Override
    public void validateOnDelete(Long userId) {
        if (articleRepository.existsByUserId(userId)) {
            throw new ObjectHaveReferencedObjects("User has articles, delete them first, " + userId);
        }
    }

    private void validationOnUpdate(User user) {
        if (user.getLogin() != null && userRepository.existsByLoginAndIdNot(user.getLogin(), user.getId())) {
            throwLoginIsInUseException();
        }
    }

    private void validationOnCreation(User user) {
        //   if (userRepository.existsByLoginValue(user.getLogin().getValue())) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throwLoginIsInUseException();
        }
    }

    private void throwLoginIsInUseException() {
        throw new LoginIsInUseException("User login is already in use");
    }
}
