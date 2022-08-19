package com.example.oauth20.repository;

import com.example.oauth20.model.Article;
import com.example.oauth20.service.dto.ArticleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


}
