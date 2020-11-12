package com.app.cms.dto.mapper;

import com.app.cms.dto.ArticleDto;
import com.app.cms.dto.converter.ArticleConverter;
import com.app.cms.entity.Article;
import com.app.cms.entity.Category;
import com.app.cms.entity.User;
import com.app.cms.repository.CategoryRepository;
import com.app.cms.repository.UserRepository;
import com.app.cms.valueobject.article.Content;
import com.app.cms.valueobject.article.Ratings;
import com.app.cms.valueobject.article.Title;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ArticleConverterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private com.fasterxml.jackson.databind.ObjectMapper jacksonModelMapper;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private ArticleConverter articleConverter;

    @Test
    public void shouldThrowError_titleIsNotSet() {
        //given
        var articleDto = articleDtoWithAllFieldsSet();
        articleDto.setTitle(null);

        //when
        assertThatThrownBy(() ->
                articleConverter.toEntity(articleDto))
                .isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Title");
    }

    @Test
    public void shouldThrowError_contentIsNotSet() {
        //given
        var articleDto = articleDtoWithAllFieldsSet();
        articleDto.setContent(null);

        //when
        assertThatThrownBy(() ->
                articleConverter.toEntity(articleDto)
        ).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Content");
    }

    @Test
    public void shouldThrowError_ratingsPositiveIsNotSet() {
        //given
        var articleDto = articleDtoWithAllFieldsSet();
        articleDto.setRatingsPositive(null);

        //when
        assertThatThrownBy(() ->
                articleConverter.toEntity(articleDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void shouldConvertDtoToEntity() {
        //given
        long categoryId = -20L;
        long userId = -30L;
        var articleDto = articleDtoWithAllFieldsSet();
        given(categoryRepository.getOne(any())).willReturn(Category.builder().id(categoryId).build());
        given(userRepository.getOne(any())).willReturn(User.builder().id(userId).build());

        //when
        var article = articleConverter.toEntity(articleDto);

        //then
        then(article).isNotNull();
        then(article.getId()).isNotNull();
        then(article.getTitle().getValue()).isEqualTo("title test");
        then(article.getContent().getValue()).isEqualTo("content test");
        then(article.getCreationDate()).isNotNull();
        then(article.getRatings().getPositive()).isEqualTo(articleDto.getRatingsPositive());
        then(article.getRatings().getNegative()).isEqualTo(articleDto.getRatingsNegative());
        then(article.getUser()).isNotNull();
        then(article.getUser().getId()).isNotNull();
        then(article.getCategory()).isNotNull();
        then(article.getCategory().getId()).isNotNull();
    }

    @Test
    public void shouldConvertEntityToDto() {
        //given
        String title = "title test";
        String content = "content test";
        long articleId = -1L;
        long userId = -5L;
        long categoryId = -10L;
        int ratingsPositive = 7;
        int ratingsNegative = 9;

        var article = Article.builder()
                .id(articleId)
                .title(Title.of(title))
                .content(Content.of(content))
                .ratings(Ratings.of(ratingsPositive, ratingsNegative))
                .user(User.builder().id(userId).build())
                .category(Category.builder().id(categoryId).build())
                .creationDate(LocalDate.now())
                .build();

        //when
        var articleDto = articleConverter.toDto(article);

        //then
        then(articleDto.getId()).isEqualTo(articleId);
        then(articleDto.getTitle()).isEqualTo(title);
        then(articleDto.getContent()).isEqualTo(content);
        then(articleDto.getCategoryId()).isEqualTo(categoryId);
        then(articleDto.getUserId()).isEqualTo(userId);
        then(articleDto.getRatingsPositive()).isEqualTo(ratingsPositive);
        then(articleDto.getRatingsNegative()).isEqualTo(ratingsNegative);
        then(articleDto.getCreationDate()).isNotNull();
    }

    @Test
    public void shouldConvertMapToEntity_AllFields() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("title", "this is new title");
        changedValues.put("ratingsPositive", 3);
        changedValues.put("ratingsNegative", 4);
        changedValues.put("content", "updated content2");
        changedValues.put("userId", 16L);
        changedValues.put("categoryId", -21);
        changedValues.put("id", -10);

        given(categoryRepository.getOne(any())).willReturn(Category.builder().id(-21L).build());
        given(userRepository.getOne(any())).willReturn(User.builder().id(-16L).build());

        //when
        Article article = articleConverter.toEntity(changedValues);

        //then
        then(article.getTitle().getValue()).isEqualTo("this is new title");
        then(article.getRatings().getPositive()).isEqualTo(3);
        then(article.getRatings().getNegative()).isEqualTo(4);
        then(article.getContent().getValue()).isEqualTo("updated content2");
        then(article.getUser().getId()).isEqualTo(-16L);
        then(article.getCategory().getId()).isEqualTo(-21L);
        then(article.getId()).isEqualTo(-10);
    }

    @Test
    public void shouldConvertMapToEntity_WithTitleAndContentOnly() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("title", "this is new title");
        changedValues.put("content", "updated content2");

        //when
        Article article = articleConverter.toEntity(changedValues);

        //then
        then(article.getTitle().getValue()).isEqualTo("this is new title");
        then(article.getContent().getValue()).isEqualTo("updated content2");
        then(article.getId()).isNull();
        then(article.getRatings()).isNull();
        then(article.getUser()).isNull();
    }

    @Test
    public void shouldConvertMapToEntity_WithRatingOnly() {
        //given
        Map<String, Object> changedValues = new HashMap<>();
        changedValues.put("ratingsPositive", 3);
        changedValues.put("ratingsNegative", 4);

        //when
        Article article = articleConverter.toEntity(changedValues);

        //then
        then(article.getRatings().getPositive()).isEqualTo(3);
        then(article.getRatings().getNegative()).isEqualTo(4);
        then(article.getTitle()).isNull();
        then(article.getContent()).isNull();
        then(article.getId()).isNull();
        then(article.getUser()).isNull();
    }

    @Test
    public void shouldConvertMapToEntity_MapIsEmpty() {
        //given
        Map<String, Object> changedValues = new HashMap<>();

        //when
        Article article = articleConverter.toEntity(changedValues);

        //then
        then(article.getRatings()).isNull();
        then(article.getTitle()).isNull();
        then(article.getContent()).isNull();
        then(article.getId()).isNull();
        then(article.getUser()).isNull();
    }

    private ArticleDto articleDtoWithAllFieldsSet() {
        return ArticleDto.builder()
                .id(-1L)
                .title("title test")
                .content("content test")
                .categoryId(-20L)
                .userId(-30L)
                .creationDate(LocalDate.now())
                .ratingsNegative(4)
                .ratingsPositive(3)
                .build();
    }
}
