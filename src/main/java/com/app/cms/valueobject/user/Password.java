package com.app.cms.valueobject.user;

import com.app.cms.error.type.PasswordNotContainsUpperAndLowercaseException;
import com.app.cms.error.type.PasswordTooLongException;
import com.app.cms.error.type.PasswordTooShortException;
import com.app.cms.error.type.PasswordsAreNotSameException;
import com.password4j.Hash;
import com.password4j.SecureString;
import lombok.*;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Arrays;

@Immutable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Embeddable
public final class Password implements Serializable {

    @Column(name = "password")
    private String hash;

    @Column(name = "salt")
    private String salt;

    public Password(String hash, String salt)
    {
        this.hash = hash;
        this.salt = salt;
    }

    public static Password of(char[] password, char[] passwordConfirm) {
        if (!Arrays.equals(password, passwordConfirm))
            throw new PasswordsAreNotSameException("Passwords are different");

        validate(password);

        Hash hash = hashPass(password);

        // Save both hash and salt
        return new Password(hash.getResult(), hash.getSalt());
    }

    private static void validate(char[] password) {
        if (password.length < 6) {
            throw new PasswordTooShortException("Password must be at least 6 characters");
        } else if (password.length > 20) {
            throw new PasswordTooLongException("Password must be 20 characters or less");
        } else if (!isContainsUpperAndLowerCase(password)) {
            throw new PasswordNotContainsUpperAndLowercaseException("Password must contain at least one lowercase and uppercase characters");
        }
    }

    private static boolean isContainsUpperAndLowerCase(char[] password) {
        int uppercaseCounter = 0;
        int lowercaseCounter = 0;

        for (int i = 0; i < password.length; i++) {
            if (Character.isUpperCase(password[i]))
                uppercaseCounter++;
            if (Character.isLowerCase(password[i]))
                lowercaseCounter++;
        }

        return uppercaseCounter > 0 && lowercaseCounter > 0;
    }

    private static Hash hashPass(char[] password) {
        // Secure the password
        SecureString securedPassword = new SecureString(password);

        // Hash the user password with a randomly generated salt
        Hash hash = com.password4j.Password.hash(securedPassword).addRandomSalt().withPBKDF2();

        // Clear the password from memory
        securedPassword.clear();

        return hash;
    }
}
