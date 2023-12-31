package com.magasin.demo.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository; // Assume you have an AuthorRepository

    public String saveAuthorAvatar(Author author, MultipartFile file) throws IOException {
        // Validate file type, size, etc. if needed
        // Extract original file extension
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);

        // Save the file to the static/images directory
        String fileName = "avatar_" + author.getId() + "_" + UUID.randomUUID() + "." + fileExtension;
        String filePath = Paths.get("src/main/resources/static/images", fileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Update the author's avatarUrl
        String avatarUrl = "/images/" + fileName;

        // delete default image if found
        if (author.getAvatarUrls().contains("/images/default.png")) {
            author.getAvatarUrls().remove("/images/default.png");
            authorRepository.save(author);
        }

        author.getAvatarUrls().add(avatarUrl);
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
