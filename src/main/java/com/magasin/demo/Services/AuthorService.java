package com.magasin.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository; // Assume you have an AuthorRepository

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Other methods for managing authors
}
