package com.cms.agregate;

import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "OS_USER")
public class User implements AggregateRoot<User, User.UserId> {
    private UserId id;
    private Login login;
    private Password password;
    private UserStatus status;
    private Email email;
    private Position position;
    private LocalDateTime creationDate;


    public static User of(Login login, Password password, Email email) {
        User user = new User();
        user.id = UserId.of(UUID.randomUUID());
        user.login = login;
        user.password = password;
        user.email = email;
        user.position = Position.USER;
        user.status = UserStatus.INACTIVE_EMAIL_CONFIRM_NEEDED;
        user.creationDate = LocalDateTime.now();

        return user;
    }

    public void changePassword(char[] newPassword, char[] newPasswordConfirm) {
        password = Password.of(newPassword, newPasswordConfirm);
    }


    public void activate() {
        if (status != UserStatus.DELETED) {
            status = UserStatus.ACTIVE;
        }
        else {
            throw new RuntimeException("Can't active deleted account");
        }
    }

    public void deactivate() {
        status = UserStatus.DEACTIVATED;
    }

    public void delete() {
        status = UserStatus.DELETED;
    }


    @Value(staticConstructor = "of")
    @Getter
    public static class UserId implements Identifier {
        private final UUID userId;
    }
}
