package com.cms.agregate;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jmolecules.ddd.annotation.ValueObject;

import java.util.regex.Pattern;

import static cms.utils.CmsValidationUtils.illegalArgumentException;
import static io.micrometer.common.util.StringUtils.isBlank;

@Value
@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Email {

    @Column(name="email")
    private String value;

    public static Email of(String email) {
        illegalArgumentException(isBlank(email), "Email is not set");
        illegalArgumentException(email.length() < 2 && email.length() > 30, "Name must be 2-30 characters");
        illegalArgumentException(!emailIsValid(email), "Email is not valid");

        return new Email(email);
    }

    private static boolean emailIsValid(String password) {
        return Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$").matcher(password).find();
    }
}
