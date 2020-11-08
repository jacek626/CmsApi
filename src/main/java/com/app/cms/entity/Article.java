package com.app.cms.entity;

import com.app.cms.valueobject.article.Content;
import com.app.cms.valueobject.article.Ratings;
import com.app.cms.valueobject.article.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Embedded
    private Title title;

    @NotNull
    @Embedded
    private Content content;

    @NotNull
    @Column(updatable = false)
    private LocalDate creationDate;

    @NotNull
    @Embedded
    private Ratings ratings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

}
