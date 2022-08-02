package com.D210.soojoobback.entity;

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

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "TITLE", length = 200)
    private String title;

    @Lob
    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "createdDate", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    protected Article() {
    }

    @Builder
    public Article(String author, String title, String contents, LocalDateTime createdDate) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }

    @Builder
    public Article(String author, String title, String contents) {
        this.author = author;
        this.title = title;
        this.contents = contents;
    }

    public void update(Article another) {
        this.title = another.title;
        this.contents = another.contents;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }



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
}
