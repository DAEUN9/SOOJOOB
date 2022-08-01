package com.example.oauth20.model;

import com.example.oauth20.service.dto.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
