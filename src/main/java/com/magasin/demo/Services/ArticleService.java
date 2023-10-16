package com.magasin.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.ArticleRepository;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> getArticlesByAuthor(Author author) {
        return articleRepository.findByAuthor(author);
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    // Other methods for managing articles
}
