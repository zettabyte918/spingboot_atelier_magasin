package com.magasin.demo.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "rest")
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByAuthor(Author author);

    @Query("select a from Article a order by a.title")
    List<Article> findByTitleArticle(String string);

    List<Article> findByTitleArticleContains(String string);
}
