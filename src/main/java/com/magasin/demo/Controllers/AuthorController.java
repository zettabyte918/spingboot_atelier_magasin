package com.magasin.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.AuthorRepository;
import com.magasin.demo.Services.AuthorService;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorRepository authorRepo;

    @GetMapping("")
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }
}
