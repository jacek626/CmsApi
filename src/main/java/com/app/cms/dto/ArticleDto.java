package com.app.cms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto extends RepresentationModel<ArticleDto> implements Serializable {

    private Long id;

    private String title;

    private String content;

    private LocalDate creationDate;
    // todo delete userId? and categoryId?
    private Long userId;

    private Long categoryId;

    private Float ratingValue;

    private Integer ratingCount;
}
