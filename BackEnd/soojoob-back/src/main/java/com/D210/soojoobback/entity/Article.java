package com.D210.soojoobback.entity;

import com.D210.soojoobback.dto.article.ArticleSaveDto;
import com.D210.soojoobback.dto.article.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "ARTICLES")
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//
//    @Column(name = "AUTHOR")
//    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Userid")
    private User user;

    @Column(name = "TITLE", length = 200, nullable = false)
    private String title;

    @Lob
    @Column(name = "CONTENTS", nullable = false)
    private String contents;

    @Column(name = "createdDate", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column
    private String articleImage;

    @Column String userName;

    protected Article() {
    }



    @Builder
    public Article(User user, String title, String contents) {
        this.user = user;
        this.title = title;
        this.contents = contents;
    }

    @Builder
    public Article(User user, String title, String contents, LocalDateTime createdDate) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    public void update(Article another) {
        this.title = another.title;
        this.contents = another.contents;
    }

    public Article(ArticleSaveDto articledto, User user){
        this.user = user;
        this.contents = articledto.getContents();
        this.title = articledto.getTitle();
        this.createdDate = getCreatedDate();
        this.articleImage = articledto.getArticleImage();
        this.userName = user.getUsername();
    }

    public static Article of(ArticleSaveDto savedto, User user){
        return new Article(savedto, user);
    }

    public boolean isrunnedBy(User user) {
        return this.user.getId().equals(user.getId());
    }
    public Long getId() {
        return id;
    }
//
//    public String getAuthor() {
//        return author;
//    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getArticleImage(){return articleImage;}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return getId().equals(article.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void confirmWriter(User user){
        this.user = user;
        user.addArticle(this);
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateContent(String contents){
        this.contents = contents;
    }
}
