package com.magasin.demo.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository; // Assume you have an AuthorRepository

    public String saveAuthorAvatar(Author author, MultipartFile file) throws IOException {
        // Validate file type, size, etc. if needed

        // Save the file to the static/images directory
        String fileName = "avatar_" + author.getId() + "_" + UUID.randomUUID() + ".png";
        String filePath = Paths.get("src/main/resources/static/images", fileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Update the author's avatarUrl
        String avatarUrl = "/images/" + fileName;
        author.setAvatarUrl(avatarUrl);
        authorRepository.save(author);

        return avatarUrl;
    }

    public Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElse(null);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    // Other methods for managing authors
}
