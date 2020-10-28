package com.app.cms.api;

import com.app.cms.dto.ArticleDto;
import com.app.cms.dto.CategoryDto;
import com.app.cms.dto.converter.ArticleConverter;
import com.app.cms.dto.converter.CategoryConverter;
import com.app.cms.repository.CategoryRepository;
import com.app.cms.service.ArticleService;
import com.app.cms.service.CategoryService;
import com.app.cms.specification.article.ArticleWithCategory;
import com.app.cms.specification.category.CategorySpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryApi {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final ArticleConverter articleConverter;

    public CategoryApi(CategoryRepository categoryRepository, CategoryConverter categoryConverter, CategoryService categoryService, ArticleService articleService, ArticleConverter articleConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @GetMapping(value = "/{categoryId}")
    @Cacheable(value = "categories", key = "#categoryId")
    public CategoryDto getCategoryById(@PathVariable Long categoryId) {
        return categoryConverter.toDto(categoryRepository.getOne(categoryId));
    }

    @GetMapping
    @Cacheable("categories")
    public Page<CategoryDto> getCategories(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                           CategorySpecification specification, Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return categoryService.get(specification, pageable).map(categoryConverter::toDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "categories")
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryConverter.toDto(categoryService.save(categoryConverter.toEntity(categoryDto)));
    }

    @PutMapping
    @CacheEvict(value = "categories", key = "#categoryDto.id")
    public CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        return categoryConverter.toDto(categoryService.save(categoryConverter.toEntity(categoryDto)));
    }

    @GetMapping(value = "/{categoryId}/articles")
    public Page<ArticleDto> getCategoryArticles(@PathVariable Long categoryId, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                Sort sort) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return articleService.get(new ArticleWithCategory(categoryId), pageable).map(articleConverter::toDto);
    }

    @DeleteMapping(value = "/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "category", key = "#categoryId")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.delete(categoryId);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity collectionOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.OPTIONS).build();
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.OPTIONS)
    public ResponseEntity singularOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS).build();
    }

}
