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
		Article a = new Article(1L, "test article", "test");
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

	@Test
	public void testDeleteArticle() {
		articleRepository.deleteById(1L);
	}

	@Test
	public void testFindAllArticle() {
		List<Article> art = articleRepository.findAll();

		for (Article a : art)
			System.out.println(a.toString());

	}

	@Test
	public void testFindArticleByTitle() {
		List<Article> arts = articleRepository.findByTitleArticle("test article");
		for (Article p : arts)
			System.out.println(p);
	}

	@Test
	public void testFindArticleByTitleContains() {
		List<Article> arts = articleRepository.findByTitleArticleContains("t");

		for (Article p : arts)
			System.out.println(p);

	}

}
