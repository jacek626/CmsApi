package com.cms.agregate;

import lombok.Getter;

import java.util.List;

@Getter
public class Article {
    private CreationDate creationDate;
    private Content content;
    private Integer version;
    private Long creatorId;
    private Status status;
    private List<History> history;

  /*  public static Article of(Content content, Long creatorId) {
        Article article = new Article();
        article.id = ArticleId.of(UUID.randomUUID());
        article.content = content;
        article.creationDate = CreationDate.of(LocalDateTime.now());
        article.version = 1;
        article.creatorId = creatorId;
        article.status = Status.NEW;
        article.history = new ArrayList<>();
        article.history.add(History.of(Status.NEW, creatorId));

        return article;
    }

    public ArticleInReviewEvent inReview(Long personId) {
        this.status = Status.REVIEW;
        history.add(History.of(Status.REVIEW, personId));
        return new ArticleInReviewEvent(this);
    }

    public ArticleAcceptedEvent accepted(Long personId) {
        this.status = Status.ACCEPTED;
        history.add(History.of(Status.ACCEPTED, personId));
        return new ArticleAcceptedEvent(this);
    }

    public ArticleRejectedEvent rejected(Long rejectedBy, String reasonOfRejection) {
        this.status = Status.REJECTED;
        history.add(History.of(Status.REJECTED, rejectedBy));
        return new ArticleRejectedEvent(this);
    }

    public ArticlePublishedEvent published(Long publishedBy) {
        this.status = Status.PUBLISHED;
        history.add(History.of(Status.REJECTED, publishedBy));
        return new ArticlePublishedEvent(this);
    }

    @Value(staticConstructor = "of")
    public static class ArticleId implements Identifier {
        private final UUID articleId;
    }*/
}
