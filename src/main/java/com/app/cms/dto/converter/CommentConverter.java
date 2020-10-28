package com.app.cms.dto.converter;

import com.app.cms.api.CommentApi;
import com.app.cms.dto.CommentDto;
import com.app.cms.entity.Comment;
import com.app.cms.repository.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentConverter implements ObjectConverter<Comment, CommentDto> {

    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    public CommentConverter(ModelMapper modelMapper, ArticleRepository articleRepository) {
        this.modelMapper = modelMapper;
        this.articleRepository = articleRepository;
    }

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        commentDto.setContent(comment.getContent().getValue());
        commentDto.setAuthor(comment.getAuthor().getValue());
        commentDto.setCreationDate(comment.getCreationDate().getValue());

        commentDto.add(
                linkTo(methodOn(CommentApi.class).get(commentDto.getId())).withSelfRel(),
                linkTo(methodOn(CommentApi.class).getAll()).withRel("comments"));

        return commentDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setArticle(articleRepository.getOne(commentDto.getArticleId()));

        return comment;
    }
}
