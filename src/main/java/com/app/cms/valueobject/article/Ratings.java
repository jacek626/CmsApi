package com.app.cms.valueobject.article;

import lombok.*;
import org.springframework.data.annotation.Immutable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Immutable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

@Embeddable
public class Ratings {

    private static final long serialVersionUID = 6732775093033061190L;

    @Column(name = "ratingsPositive")
    private Integer positive;

    @Column(name = "ratingsNegative")
    private Integer negative;

    public static Ratings of(Integer positive, Integer negative) {
        if (positive == null || negative == null)
            throw new IllegalArgumentException("Rating values must be set");

        if (positive < 0 || negative < 0)
            throw new IllegalArgumentException("Rating must 0 or more");

        return new Ratings(positive, negative);
    }

    public static Ratings create() {
        return new Ratings(0, 0);
    }
}
