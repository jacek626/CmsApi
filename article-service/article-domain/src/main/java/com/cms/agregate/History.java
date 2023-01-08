package com.cms.agregate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
//@ValueObject
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class History {

    private Status status;
    private LocalDateTime date;
    private Long personId;

    public static History of(Status status, Long personId, LocalDateTime date) {
        var history = new History(status, date, personId);
        return history;
    }

    public static History of(Status status, Long personId) {
        return of(status, personId, LocalDateTime.now());
    }
}

