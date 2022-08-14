package com.D210.soojoobback.dto.article;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdDate;
    private String articleImage;
    private String userName;

//    public ArticleDto toEntity(){
//        ArticleDto articleResponseDto = ArticleDto.builder()
//                .id(id)
//                .title(title)
//                .contents(contents)
//                .articleImage(articleImage)
//                .build();
//        return articleResponseDto;
//    }

    @Builder
    public ArticleDto(Long id, String title, String contents, LocalDateTime createdDate, String articleImage, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.articleImage = articleImage;
        this.userName = userName;
    }
//    public Long getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getContents() {return contents;}
//
//    public LocalDateTime getCreatedDate() {return createdDate;}
//
//    public String getArticleImage() {return articleImage;}

}
