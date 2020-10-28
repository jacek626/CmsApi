package com.app.cms.api;

import com.app.cms.dto.CommentDto;
import com.app.cms.dto.converter.CommentConverter;
import com.app.cms.repository.CommentRepository;
import com.app.cms.service.CommentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/comments")
public class CommentApi {

    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final CommentConverter commentConverter;

    public CommentApi(CommentRepository commentRepository, CommentService commentService, CommentConverter commentConverter) {
        this.commentRepository = commentRepository;
        this.commentService = commentService;
        this.commentConverter = commentConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "comments")
    public CommentDto createComment(@RequestBody CommentDto commentDto) {
        return commentConverter.toDto(commentService.save(commentConverter.toEntity(commentDto)));
    }

    @GetMapping(value = "/{commentId}")
    @Cacheable(value = "comments", key = "#commentId")
    public CommentDto get(@PathVariable Long commentId) {
        return commentConverter.toDto(commentRepository.getOne(commentId));
    }

    @GetMapping
    @Cacheable(value = "comments")
    public CollectionModel<CommentDto> getAll() {
        Link link = linkTo(methodOn(CommentApi.class).getAll()).withSelfRel();
        return CollectionModel.of(commentRepository.findAll().stream().map(commentConverter::toDto).collect(Collectors.toList()), link);
    }

    @DeleteMapping(value = "/{commentId}")
    @CacheEvict(value = "comments", key = "#commentId")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }

    @PutMapping
    @CacheEvict(value = "comments", key = "#commentDto.id")
    public CommentDto updateComment(@RequestBody CommentDto commentDto) {
        return commentConverter.toDto(commentService.save(commentConverter.toEntity(commentDto)));
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity collectionOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.OPTIONS).build();
    }

    @RequestMapping(value = "/{commentId}", method = RequestMethod.OPTIONS)
    public ResponseEntity singularOptions() {
        return ResponseEntity.ok().allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS).build();
    }
}
