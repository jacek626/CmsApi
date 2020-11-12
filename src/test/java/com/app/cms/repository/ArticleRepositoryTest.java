package com.app.cms.repository;

import com.app.cms.entity.Article;
import com.app.cms.specification.article.ArticleWithCreationDate;
import com.app.cms.specification.article.ArticleWithTitle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void shouldUpdatePartially_titleAndContent() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("title", "this is new title45452");
        changedValues.put("content", "updated content145676");

        //when
        articleRepository.updatePartially(-1L, changedValues);

        //then
        var savedArticle = articleRepository.getOne(-1L);
        then(savedArticle.getTitle().getValue()).isEqualTo("this is new title45452");
        then(savedArticle.getContent().getValue()).isEqualTo("updated content145676");
    }

    @Test
    public void shouldUpdatePartially_userAndCategory() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("user", -2L);
        changedValues.put("category", -2L);

        //when
        articleRepository.updatePartially(-1L, changedValues);

        //then
        var savedArticle = articleRepository.getOne(-1L);
        then(savedArticle.getUser().getId()).isEqualTo(-2);
        then(savedArticle.getCategory().getId()).isEqualTo(-2);
    }

    @Test
    public void shouldUpdatePartially_ratingValueAndRatingCount() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("ratingsPositive", 4);
        changedValues.put("ratingsNegative", 7);

        //when
        articleRepository.updatePartially(-1L, changedValues);

        //then
        var savedArticle = articleRepository.getOne(-1L);
        then(savedArticle.getRatings().getPositive()).isEqualTo(4);
        then(savedArticle.getRatings().getNegative()).isEqualTo(7);
    }

    @Test
    public void shouldReturnArticlesByTitle() {
        //given
        var pageable = PageRequest.of(0, 10);
        var articleSpecification = new ArticleWithTitle("Article1 title");

        //when
        Page<Article> articles = articleRepository.findAll(articleSpecification, pageable);

        //then
        assertThat(articles).isNotEmpty();
    }

    @Test
    public void shouldReturnArticlesByCreationDate() {
        //given
        var pageable = PageRequest.of(0, 10);
        var articleSpecification = new ArticleWithCreationDate(LocalDate.of(2020, 1, 1), LocalDate.now().plusDays(1));

        //when
        Page<Article> articles = articleRepository.findAll(articleSpecification, pageable);

        //then
        assertThat(articles).isNotEmpty();
    }
}



