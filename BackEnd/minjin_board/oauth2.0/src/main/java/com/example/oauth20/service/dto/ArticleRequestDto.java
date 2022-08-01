package com.example.oauth20.service.dto;

import java.time.LocalDateTime;

public class ArticleRequestDto extends BaseTimeEntity {
    private String author;
    private String title;
    private String contents;

    private LocalDateTime createdDate;


    public ArticleRequestDto(String author, String title, String contents, LocalDateTime createdDate) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;

    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() { return contents; }

}
