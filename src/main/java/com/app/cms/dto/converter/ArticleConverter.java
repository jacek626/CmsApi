package com.app.cms.dto.converter;

import com.app.cms.api.ArticleApi;
import com.app.cms.dto.ArticleDto;
import com.app.cms.entity.Article;
import com.app.cms.repository.CategoryRepository;
import com.app.cms.repository.UserRepository;
import com.app.cms.valueobject.article.Content;
import com.app.cms.valueobject.article.Rating;
import com.app.cms.valueobject.article.Title;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ArticleConverter implements ObjectConverter<Article, ArticleDto> {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final com.fasterxml.jackson.databind.ObjectMapper jacksonModelMapper;

    public ArticleConverter(ModelMapper modelMapper, UserRepository userRepository, CategoryRepository categoryRepository, ObjectMapper jacksonModelMapper) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.jacksonModelMapper = jacksonModelMapper;
    }

    @Override
    public ArticleDto toDto(Article article) {
        var articleDto = new ArticleDto();
        articleDto.setUserId(article.getUser().getId());
        articleDto.setCategoryId(article.getCategory().getId());
        articleDto.setId(article.getId());
        articleDto.setCreationDate(article.getCreationDate());
        articleDto.setContent(article.getContent().getValue());
        articleDto.setRatingValue(article.getRating().getValue());
        articleDto.setRatingCount(article.getRating().getCount());
        articleDto.setTitle(article.getTitle().getValue());

        articleDto.add(
                linkTo(methodOn(ArticleApi.class).getArticleById(article.getId())).withSelfRel(),
                linkTo(methodOn(ArticleApi.class).getArticles(0, 10, null, null)).withRel("articles"));

        return articleDto;
    }

    public Article toEntity(Map<String, Object> objectAsMap) {
        var articleDto = jacksonModelMapper.convertValue(objectAsMap, ArticleDto.class);

        return toEntity(articleDto, ConvertType.PART_OF_FIELDS_CAN_BE_SET);
    }

    @Override
    public Article toEntity(ArticleDto articleDto) {
        return toEntity(articleDto, ConvertType.ALL_FIELDS_MUST_BE_SET);
    }

    private Article toEntity(ArticleDto articleDto, ConvertType convertType) {
        Article article = modelMapper.map(articleDto, Article.class);

        if(articleDto.getCategoryId() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            article.setCategory(categoryRepository.getOne(articleDto.getCategoryId()));

        if(articleDto.getUserId() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            article.setUser(userRepository.getOne(articleDto.getUserId()));

        if(articleDto.getTitle() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            article.setTitle(Title.of(articleDto.getTitle()));

        if(articleDto.getContent() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            article.setContent(Content.of(articleDto.getContent()));

        if(articleDto.getRatingValue() != null || articleDto.getRatingCount() != null || convertType == ConvertType.ALL_FIELDS_MUST_BE_SET)
            article.setRating(Rating.of(articleDto.getRatingValue(), articleDto.getRatingCount()));

        return article;
    }
}
