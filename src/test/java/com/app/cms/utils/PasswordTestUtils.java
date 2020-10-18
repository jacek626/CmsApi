package com.app.cms.utils;


import com.app.cms.valueobject.user.Password;

public class PasswordTestUtils {

    public static boolean checkPasswordsAreEquals(CharSequence plainTextPassword, Password password) {
        return com.password4j.Password.check(plainTextPassword, password.getValue()).addSalt(password.getSalt()).withPBKDF2();
    }
}
