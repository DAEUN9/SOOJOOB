package com.D210.soojoobback.dto.article;

import com.D210.soojoobback.entity.Article;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ArticleSaveDto extends BaseTimeEntity{

    private Long id;
    private Long userId;

    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdDate;

    @Builder
    public ArticleSaveDto(Article article) {
        this.id = article.getId();
        this.userId = article.getUser().getId();
        this.username = article.getUser().getUsername();
        this.title = article.getTitle();
        this.contents = article.getContents();
        this.createdDate = article.getCreatedDate();
    }
    public ArticleSaveDto(Long id, Long userId, String username, String title,String contents, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.title = title;
        this.username = username;
        this.contents = contents;
        this.createdDate = createdDate;
    }

}
