package com.cms.agregate;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.jmolecules.ddd.annotation.ValueObject;

import static cms.utils.CmsValidationUtils.illegalArgumentException;
import static com.google.common.base.Strings.isNullOrEmpty;

@Value
@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {

    @Column(name = "login")
    private String value;

    public static Login of(String name) {
        illegalArgumentException(isNullOrEmpty(name), "Name is not set");
        illegalArgumentException(name.length() < 2 && name.length() < 30, "Name must be 2-30 characters");

        return new Login(name);
    }

    public String getValue() {
        return value;
    }
}
