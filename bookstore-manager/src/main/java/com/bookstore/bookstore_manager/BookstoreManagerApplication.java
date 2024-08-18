package com.bookstore.bookstore_manager;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.bookstore.bookstore_manager.Entity.AuthorEntity;
import com.bookstore.bookstore_manager.Entity.BookEntity;
import com.bookstore.bookstore_manager.repository.AuthorRepository;
import com.bookstore.bookstore_manager.repository.BookStoreRepository;

@SpringBootApplication
public class BookstoreManagerApplication {

	private static final Logger log = LoggerFactory.getLogger(BookstoreManagerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreManagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(BookStoreRepository bookStoreRepository, AuthorRepository authorRepository) {
		
		return (args) -> {
			// add a few books
			BookEntity bookEntity1 = new BookEntity("Harry Potter and the half blood prince",2004,20.20,"fantasy");
			List<AuthorEntity> authors = new ArrayList<AuthorEntity>();
			authors.add(new AuthorEntity("JK Rowling", "04-04-1970"));
			authors.add(new AuthorEntity("JK Rowling 2", "03-04-1970"));
			for(AuthorEntity author : authors) {
				author.setBookEntity(bookEntity1);
			}
			bookEntity1.setAuthors(authors);
			bookStoreRepository.save(bookEntity1);
			authorRepository.saveAll(authors);
			authors.clear();

			BookEntity bookEntity2 = new BookEntity("Twilight",2005,30.20,"horror");
			authors.add(new AuthorEntity("Stephanie Meyer", "04-04-1974"));
			authors.add(new AuthorEntity("Stephanie Meyer jr", "03-04-2004"));
			for(AuthorEntity author : authors) {
				author.setBookEntity(bookEntity2);
			}
			bookEntity2.setAuthors(authors);
			bookStoreRepository.save(bookEntity2);
			authorRepository.saveAll(authors);
			authors.clear();

			BookEntity bookEntity3 = new BookEntity("The Shining",2013,10.20,"horror, adventure");
			authors.add(new AuthorEntity("Stephen King", "21-09-1947"));
			for(AuthorEntity author : authors) {
				author.setBookEntity(bookEntity3);
			}
			bookEntity3.setAuthors(authors);
			bookStoreRepository.save(bookEntity3);
			authorRepository.saveAll(authors);
			authors.clear();

			// fetch all Books
			log.info("Books found with findAll():");
			log.info("-------------------------------");
			bookStoreRepository.findAll().forEach(book -> {
				log.info(book.toString());
			});
		};
	}

}
