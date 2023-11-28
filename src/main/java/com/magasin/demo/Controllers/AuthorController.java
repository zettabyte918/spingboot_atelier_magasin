package com.magasin.demo.Controllers;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
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
    public ResponseEntity<Map<String, Object>> handleAvatarUpload(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            // Validate if the author exists
            Author author = authorService.getAuthorById(id);
            if (author == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createResponse(false, "Author not found"));
            }

            // Check if the uploaded image contains a face using the face detection API
            Boolean hasFace = handleDjangoResponse(file, "http://localhost:8000/api/detect-face/");
            if (hasFace) {
                // Process the uploaded file and update the author's avatarUrl
                String avatarUrl = authorService.saveAuthorAvatar(author, file);
                return ResponseEntity.status(HttpStatus.CREATED).body(createResponse(true, "Avatar containt a face"));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createResponse(false, "Avatar does not contain a face"));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createResponse(true, "Error uploading avatar"));
        }
    }

    private Map<String, Object> createResponse(boolean success, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        response.put("message", message);
        return response;
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
        // Set default avatar URL if the list is empty
        if (author.getAvatarUrls() == null || author.getAvatarUrls().isEmpty()) {
            List<String> defaultAvatarUrls = new ArrayList<>();
            defaultAvatarUrls.add("/images/default.png");
            author.setAvatarUrls(defaultAvatarUrls);
        }

        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    public Boolean handleDjangoResponse(MultipartFile file, String apiUrl) {
        try {
            // Use the uploadImage method to send the file to Django
            ResponseEntity<Map<String, Boolean>> responseEntity = uploadImage(file, apiUrl);

            // Extract the "face" attribute from the response
            Map<String, Boolean> responseBody = responseEntity.getBody();
            return responseBody != null ? responseBody.get("face") : null;

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
    }

    private ResponseEntity<Map<String, Boolean>> uploadImage(MultipartFile file, String apiUrl) throws Exception {
        // Convert MultipartFile to byte array
        byte[] fileBytes = file.getBytes();

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create a ByteArrayResource from the file bytes
        ByteArrayResource resource = new ByteArrayResource(fileBytes) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        // Create a MultiValueMap to represent the form data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", resource);

        // Create a RequestEntity with headers and the MultiValueMap
        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity.post(new URI(apiUrl))
                .headers(headers)
                .body(body);

        // Make a request to the face detection API with explicit generic type
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<Map<String, Boolean>> responseType = new ParameterizedTypeReference<Map<String, Boolean>>() {
        };
        return restTemplate.exchange(requestEntity, responseType);
    }

}
