package com.app.cms.api;

import com.app.cms.dto.ArticleDto;
import com.app.cms.dto.CommentDto;
import com.app.cms.dto.converter.ArticleConverter;
import com.app.cms.dto.converter.CommentConverter;
import com.app.cms.repository.ArticleRepository;
import com.app.cms.repository.CommentRepository;
import com.app.cms.service.ArticleService;
import com.app.cms.specification.article.ArticleSpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/articles")
public class ArticleApi {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final ArticleConverter articleConverter;
    private final CommentConverter commentConverter;
    private final CommentRepository commentRepository;

    public ArticleApi(ArticleService articleService, ArticleRepository articleRepository, ArticleConverter articleConverter, CommentConverter commentConverter, CommentRepository commentRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
        this.articleConverter = articleConverter;
        this.commentConverter = commentConverter;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "articles")
    public ArticleDto createArticle(@RequestBody ArticleDto article) {
        return articleConverter.toDto(articleService.save(articleConverter.toEntity(article)));
    }

    @GetMapping(value = "/{articleId}")
    @Cacheable(value = "articles", key = "#articleId")
    public ArticleDto getArticleById(@PathVariable Long articleId) {
        return articleRepository.findById(articleId).map(articleConverter::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Article not found"));
    }

    @GetMapping(value = "/{articleId}/comments")
    @Cacheable(value = "articles", key = "#articleId")
    public CollectionModel<CommentDto> getArticleComments(@PathVariable Long articleId) {
        Link link = linkTo(methodOn(ArticleApi.class).getArticleComments(articleId)).withSelfRel();
        return CollectionModel.of(commentRepository.findByArticleId(articleId).stream().map(commentConverter::toDto).collect(Collectors.toList()), link);
    }

    @GetMapping
    @Cacheable("articles")
    public Page<ArticleDto> getArticles(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                           ArticleSpecification specification, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleService.get(specification, pageable).map(articleConverter::toDto);
    }

    @DeleteMapping(value = "/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "articles", key = "#articleId")
    public void deleteArticle(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    @CacheEvict(value = "articles", key = "#articleDto.id")
    public ArticleDto updateArticle(@RequestBody ArticleDto articleDto) {
        return articleConverter.toDto(articleService.update(articleConverter.toEntity(articleDto)));
    }

    @PatchMapping(value = "/{articleId}", consumes = "application/json-patch+json")
    @CacheEvict(value = "articles", key = "#articleId")
    public void updateArticle(@PathVariable Long articleId, @RequestBody HashMap<String, Object> changedValues) {
        articleService.updatePartially(articleId, changedValues);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity collectionOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.OPTIONS).build();
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.OPTIONS)
    public ResponseEntity singularOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.OPTIONS).build();
    }
}
