package com.cms.agregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
//@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreationDate {

    LocalDateTime date;

    public static CreationDate of(LocalDateTime date) {
      //  new IllegalArgumentException(isNull(date), "Date is required");

        return new CreationDate(date);
    }

}
