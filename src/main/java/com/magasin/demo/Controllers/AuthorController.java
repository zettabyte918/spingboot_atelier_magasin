package com.magasin.demo.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.ArticleRepository;
import com.magasin.demo.Repository.AuthorRepository;
import com.magasin.demo.Services.ArticleService;
import com.magasin.demo.Services.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private ArticleRepository articleRepo;

    @PostMapping("/{id}/upload-avatar")
    public ResponseEntity<String> handleAvatarUpload(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Validate if the author exists
            Author author = authorService.getAuthorById(id);
            if (author == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author not found");
            }

            // Process the uploaded file and update the author's avatarUrl
            String avatarUrl = authorService.saveAuthorAvatar(author, file);

            return ResponseEntity.ok("Avatar uploaded successfully. Avatar URL: " + avatarUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading avatar");
        }
    }

    @GetMapping("")
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    @GetMapping("{id}/articles")
    public ResponseEntity<List<Article>> getAllAuthorsArticles(@PathVariable Long id) {
        Author au = authorService.getAuthorById(id);
        List<Article> arts = articleService.getArticlesByAuthor(au);
        return ResponseEntity.status(HttpStatus.CREATED).body(arts);
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        author.setAvatarUrl("/images/default.png");
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
}
