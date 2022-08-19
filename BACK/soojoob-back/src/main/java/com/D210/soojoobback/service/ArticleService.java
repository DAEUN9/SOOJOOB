package com.D210.soojoobback.service;

import com.D210.soojoobback.dto.article.ArticleSaveDto;
import com.D210.soojoobback.entity.Article;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.repository.ArticleRepository;
import com.D210.soojoobback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;



    @Transactional(readOnly = true)
    public List<ArticleSaveDto> showAll() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(article -> new ArticleSaveDto(article.getId(),
                        article.getTitle(),
                        article.getContents(),
                        article.getCreatedDate(),
                        article.getArticleImage(),
                        article.getUserName()
                ))
                .collect(Collectors.toList());

    }

    @Transactional(readOnly = true)
    public ArticleSaveDto showDetail(Long id){
        Article article = getArticleById(id);
        ArticleSaveDto detailArticle = new ArticleSaveDto(article);
        return detailArticle;
    }

    public Article getArticleById(Long id){
        return articleRepository.findById(id).orElseThrow(
                () -> new CustomErrorException("게시글을 찾을 수 없습니다.")
        );
    }
    @Transactional
    public List<ArticleSaveDto> searchTitle(String keyword){
        return articleRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(ArticleSaveDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleSaveDto> showUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()->new CustomErrorException("유저 정보가 없습니다.")
        );
        List<Article> UserList = user.getArticleList();
        List<ArticleSaveDto> userArticleList = new ArrayList<>();
        for(Article article : UserList){
            ArticleSaveDto addDto = new ArticleSaveDto(article);
            userArticleList.add(addDto);
        }
        return userArticleList;
    }

    @Transactional
    public void save(ArticleSaveDto articleSaveDto, User user){
        Article article = Article.of(articleSaveDto, user);

        Long userId = user.getId();
        User Articleuser = userRepository.findById(userId).orElseThrow(
                () -> new CustomErrorException("유저 정보를 찾을 수 없습니다.")
        );
        List<Article> articleList = Articleuser.getArticleList();
        articleList.add(article);

        articleRepository.save(article);
    }

    @Transactional
    public void update(Long articleId, ArticleSaveDto articleUpdateRequestDto) {
        System.out.println("update service1");

        Article sourceArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        System.out.println("update service");

        sourceArticle.updateTitle(articleUpdateRequestDto.getTitle());
        sourceArticle.updateContent(articleUpdateRequestDto.getContents());

    }

    @Transactional
    public void delete(Long articleId, User user) {
        Article deleteArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        if(deleteArticle.isrunnedBy(user)) {
            articleRepository.delete(deleteArticle);
        } else {
            throw new CustomErrorException("잘못된 사용자 입니다.");
        }

    }


}
