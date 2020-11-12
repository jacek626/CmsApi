package com.app.cms.valueobject.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
public class RatingTest {

    @Test
    public void shouldCreateRating() {
        // given, when
        var ratings = Ratings.of(1, 2);

        //then
        assertThat(ratings.getPositive()).isEqualTo(1);
        assertThat(ratings.getNegative()).isEqualTo(2);
    }

    @Test
    public void shouldCreateRating_zeroValues() {
        // given, when
        var ratings = Ratings.of(0, 0);

        //then
        assertThat(ratings.getPositive()).isEqualTo(0);
        assertThat(ratings.getNegative()).isEqualTo(0);
    }

    @Test
    public void shouldNotCreateRating_ratingsPositiveIsBelowZero() {
        assertThatThrownBy(() -> {
            Ratings.of(-1, 2);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotCreateRating_ratingValueIsNull() {
        assertThatThrownBy(() ->
                Ratings.of(null, 2)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotCreateRating_ratingsNegativeIsBelowZero() {
        assertThatThrownBy(() ->
                Ratings.of(6, -2)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotCreateRating_ratingCountIsNegative() {
        assertThatThrownBy(() ->
                Ratings.of(1, -2)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldNotCreateRating_ratingCountIsNull() {
        assertThatThrownBy(() ->
                Ratings.of(1, null)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
