package com.D210.soojoobback.dto.article;

import lombok.Builder;

import java.time.LocalDateTime;

public class ArticleDto {
    private Long id;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    public ArticleDto toEntity(){
        ArticleDto articleResponseDto = ArticleDto.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .build();
        return articleResponseDto;
    }

    @Builder
    public ArticleDto(Long id, String title, String contents, LocalDateTime createdDate) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
    }
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {return contents;}

    public LocalDateTime getCreatedDate() {return createdDate;}

}
