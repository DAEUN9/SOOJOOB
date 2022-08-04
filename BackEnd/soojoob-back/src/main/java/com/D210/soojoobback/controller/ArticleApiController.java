package com.D210.soojoobback.controller;

import com.D210.soojoobback.dto.article.ArticleDto;
import com.D210.soojoobback.dto.user.ResponseDto;
import com.D210.soojoobback.entity.User;
import com.D210.soojoobback.exception.CustomErrorException;
import com.D210.soojoobback.security.JwtTokenProvider;
import com.D210.soojoobback.security.UserDetailsImpl;
import com.D210.soojoobback.service.ArticleService;
import com.D210.soojoobback.dto.article.ArticleSaveDto;
import com.D210.soojoobback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/article")
public class ArticleApiController {
    private  final ArticleService articleService;

    private UserService userService;

    @PostMapping("")
    public ResponseDto save(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @RequestBody ArticleSaveDto articlesavedto){
        checkLogin(userDetailsImpl);
        User user = userDetailsImpl.getUser();
        articleService.save(articlesavedto, user);
        return new ResponseDto(201L,"게시글 생성에 성공했습니다 !", "");
    }
    private void checkLogin(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println(userDetails);
        if (userDetails == null) {
            throw new CustomErrorException("로그인이 필요합니다.2");
        }
    }
    @GetMapping("")
    public ResponseEntity showAll() {
        List<ArticleDto> articles = articleService.showAll();
        return ResponseEntity.ok(articles);
    }
    @GetMapping("/{user_id}")
    public ResponseDto showUser(@PathVariable("user_id") Long userId){
        List<ArticleSaveDto> article = articleService.showUser(userId);
        return new ResponseDto(200L, "해당 유저의 게시판 불러오기 성공", article);
    }

    @GetMapping("/search")
    public ResponseDto searchByTitle(@RequestParam(value = "keyword", required = false) String keyword){
        List<ArticleSaveDto> article = articleService.searchTitle(keyword);
        return new ResponseDto(200L, "keyword가 포함된 게시판 불러오기 성공", article);
    }

    @PutMapping("/{articleId}")
    public void update(@PathVariable("articleId") Long articleId,
                       @RequestBody ArticleSaveDto articleUpdateRequestDto) {
        articleService.update(articleId, articleUpdateRequestDto);

    }

    @DeleteMapping("/{article_id}")
    public ResponseDto delete(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,  @PathVariable("article_id") Long articleId){

        User user = userDetailsImpl.getUser();
        articleService.delete(articleId, user);
        return new ResponseDto(204L, "게시글 삭제에 성공하였습니다.", "");
    }



}
