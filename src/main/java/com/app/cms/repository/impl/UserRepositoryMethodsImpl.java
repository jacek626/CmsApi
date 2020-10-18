package com.app.cms.repository.impl;

import com.app.cms.repository.UserRepositoryMethods;
import com.app.cms.repository.utils.QueryBuilder;
import com.app.cms.valueobject.user.Password;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;
import java.util.Objects;


@Repository
public class UserRepositoryMethodsImpl implements UserRepositoryMethods {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void updatePartially(long userId, Map<String, Object> changedValues) {
        handlePasswordInPartialUpdate(changedValues);

        new QueryBuilder("Users").addParameters(changedValues).whereId(userId).runQuery(entityManager);
    }

    private void handlePasswordInPartialUpdate(Map<String, Object> changedValues) {
        if (changedValues.containsKey("password")) {
            Objects.requireNonNull(changedValues.get("password"));
            Objects.requireNonNull(changedValues.get("passwordConfirm"));

            String passwordValue = (String) changedValues.get("password");
            String passwordConfirm = (String) changedValues.get("passwordConfirm");

            var password = Password.of(passwordValue.toCharArray(), passwordConfirm.toCharArray());
            changedValues.put("password", password.getValue());
            changedValues.put("salt", password.getSalt());
            changedValues.remove("passwordConfirm");
        }
    }
}
