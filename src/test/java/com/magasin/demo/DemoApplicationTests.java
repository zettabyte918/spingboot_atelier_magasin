package com.magasin.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	public void testFindAuthors() {
		// Produit p = produitRepository.findById(2L).get();
		List<Author> a = authorRepository.findAll();
		System.out.println("*****************************");
		System.out.println(a.toString());
	}

	@Test
	public void testCreateArticle() {
		Article a = new Article("test article", "test");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse("2023-10-10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Author au = new Author("hoss test", "last", "test@test.com", "22222", date);
		authorRepository.save(au);

		a.setAuthor(au);
		articleRepository.save(a);
	}

	@Test
	public void testCreateAuthor() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse("2023-10-10");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Author au = new Author("hoss test", "last", "test@test.com", "22222", date);
		authorRepository.save(au);
	}

}
