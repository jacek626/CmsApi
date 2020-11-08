package com.app.cms.valueobject.user;

import com.app.cms.error.type.InvalidLoginException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;


@Immutable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Embeddable
public final class Login {

    @Column(name = "login")
    private String value;

    public static Login of(String value) {
        if (StringUtils.isBlank(value) || value.length() < 3 || value.length() > 20)
            throw new InvalidLoginException("Login should be between 3 and 20 letters");

        return new Login(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Login login = (Login) o;
        return Objects.equals(value, login.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
