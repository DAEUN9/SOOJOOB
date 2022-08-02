package com.example.oauth20.service;

import com.example.oauth20.model.Article;
import com.example.oauth20.repository.ArticleRepository;
import com.example.oauth20.service.dto.ArticleDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ArticleService {
    private ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public ArticleDto create(ArticleDto articleRequestDto) {
        Article article = new Article(articleRequestDto.getAuthor(),
                articleRequestDto.getTitle(),
                articleRequestDto.getContents(),
                articleRequestDto.getCreatedDate());

        Article savedArticle = articleRepository.save(article);
        log.info(savedArticle.getCreatedDate());
        return new ArticleDto(savedArticle.getId(),
                savedArticle.getAuthor(),
                savedArticle.getTitle(),
                savedArticle.getContents(),
                savedArticle.getCreatedDate());
    }

    @Transactional(readOnly = true)
    public List<ArticleDto> showAll() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(article -> new ArticleDto(article.getId(),
                        article.getAuthor(),
                        article.getTitle(),
                        article.getContents(),
                        article.getCreatedDate()))
                .collect(Collectors.toList())
                ;
    }

    @Transactional
    public ArticleDto update(Long articleId, ArticleDto articleUpdateRequestDto) {
        Article sourceArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        Article targetArticle = new Article(articleUpdateRequestDto.getAuthor(),
                articleUpdateRequestDto.getTitle(), articleUpdateRequestDto.getContents(), articleUpdateRequestDto.getCreatedDate());

        sourceArticle.update(targetArticle);

        return new ArticleDto(sourceArticle.getId(),
                sourceArticle.getAuthor(),
                sourceArticle.getTitle(),
                sourceArticle.getContents(),
                sourceArticle.getCreatedDate());
    }

    @Transactional
    public void delete(Long articleId) {
        Article deleteArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));

        articleRepository.delete(deleteArticle);
    }
}
