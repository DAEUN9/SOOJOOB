package com.example.oauth20.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ArticleMapper {
    List<Map<String, Object>> getArticle();
}
