package com.magasin.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(Author author);
}
