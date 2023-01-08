package com.cms.agregate;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.ArrayUtils;
import org.jmolecules.ddd.annotation.ValueObject;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.regex.Pattern;

import static cms.utils.CmsValidationUtils.illegalArgumentException;

@Value
@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {

    @Column(name ="password")
    private char[] value;

    public static Password of(char[] password, char[] passwordConfirm) {
        illegalArgumentException(ArrayUtils.isEmpty(password), "Password is not set");
        illegalArgumentException(ArrayUtils.isEmpty(password), "Password confirm is not set");
        illegalArgumentException(!Arrays.equals(password, passwordConfirm), "Passwords are not equal");
        illegalArgumentException(password.length < 6 || password.length > 30, "Password must be 6-30 characters");
        illegalArgumentException(!containsNumbersAndLetters(password), "Password must include upper and lowercase letters, numbers");

        return new Password(password);
    }

    private static boolean containsNumbersAndLetters(char[] password) {
       return Pattern.matches("(([A-Z].*[0-9])|([0-9].*[A-Z]))", CharBuffer.wrap(password));
    }
}
