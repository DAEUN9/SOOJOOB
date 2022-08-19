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
   private String title;
    private String contents;
    private LocalDateTime createdDate;
    private String articleImage;
    private String userName;

    @Builder
    public ArticleSaveDto(Article article) {
       this.id = article.getId();
       this.userId = article.getUser().getId();
       this.title = article.getTitle();
       this.contents = article.getContents();
       this.createdDate = article.getCreatedDate();
       this.articleImage = article.getArticleImage();
       this.userName = article.getUser().getUsername();
    }
    public ArticleSaveDto(Long id, String title, String contents, LocalDateTime createdDate, String articleImage, String userName) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createdDate = createdDate;
        this.articleImage = articleImage;
        this.userName = userName;
    }

}
