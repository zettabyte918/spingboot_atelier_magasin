package com.magasin.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.ArticleRepository;
import com.magasin.demo.Services.ArticleService;
import com.magasin.demo.Services.AuthorService;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepo;

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public List<Article> getAllArticle() {
        return articleRepo.findAll();
    }

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

    @PutMapping
    public ResponseEntity<String> updateArticle(@RequestBody Article article, @RequestParam Long authorId) {
        // Retrieve the author based on the provided authorId
        Author author = authorService.getAuthorById(authorId);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        article.setAuthor(author);
        articleRepo.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body("updated");
    }
}
