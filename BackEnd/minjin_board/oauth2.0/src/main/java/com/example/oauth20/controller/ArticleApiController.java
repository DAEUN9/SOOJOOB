package com.example.oauth20.controller;

import com.example.oauth20.service.ArticleService;
import com.example.oauth20.service.dto.ArticleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ArticleApiController {
    private ArticleService articleService;

    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    public ResponseEntity create(@RequestBody ArticleDto articleRequestDto) {
        ArticleDto createdArticle = articleService.create(articleRequestDto);

        return ResponseEntity
                .created(URI.create("/articles/" + createdArticle.getId()))
                .body(createdArticle);
    }

    @GetMapping("/articles")
    public ResponseEntity showAll() {
        List<ArticleDto> articles = articleService.showAll();
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity update(@PathVariable("articleId") Long articleId,
                                 @RequestBody ArticleDto articleUpdateRequestDto) {
        ArticleDto updatedArticle = articleService.update(articleId, articleUpdateRequestDto);

        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity delete(@PathVariable("articleId") Long articleId) {
        articleService.delete(articleId);

        return ResponseEntity.ok().build();
    }
}
