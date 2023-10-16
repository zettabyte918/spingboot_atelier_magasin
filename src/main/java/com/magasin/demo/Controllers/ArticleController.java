package com.magasin.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Services.ArticleService;
import com.magasin.demo.Services.AuthorService;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article, @RequestParam Long authorId) {
        // Retrieve the author based on the provided authorId
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        article.setAuthor(author);
        Article createdArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }
}
