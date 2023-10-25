package com.magasin.demo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.magasin.demo.Entity.Article;
import com.magasin.demo.Entity.Author;
import com.magasin.demo.Repository.ArticleRepository;
import com.magasin.demo.Repository.AuthorRepository;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testFindArticles() {
		// Produit p = produitRepository.findById(2L).get();
		List<Article> a = articleRepository.findAll();
		System.out.println("*****************************");
		System.out.println(a.toString());
	}

	@Test
	public void testCreateArticle() {
		Article a = new Article("test article", "test");
		Author au = new Author("hoss test", "test@test.com");
		authorRepository.save(au);

		a.setAuthor(au);
		articleRepository.save(a);
	}

	@Test
	public void testUpdateArticle() {
		Article a = articleRepository.findById(1L).get();
		a.setContent("test edited");
		articleRepository.save(a);
		System.out.println(a);
	}

}
