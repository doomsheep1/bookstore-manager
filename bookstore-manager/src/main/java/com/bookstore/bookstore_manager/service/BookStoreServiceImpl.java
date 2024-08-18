package com.bookstore.bookstore_manager.service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.Year;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bookstore.bookstore_manager.Entity.AuthorEntity;
import com.bookstore.bookstore_manager.Entity.BookEntity;
import com.bookstore.bookstore_manager.Entity.BookResponse;
import com.bookstore.bookstore_manager.repository.AuthorRepository;
import com.bookstore.bookstore_manager.repository.BookStoreRepository;

@Service
public class BookStoreServiceImpl implements BookStoreService {

    private static final Logger log = LoggerFactory.getLogger(BookStoreServiceImpl.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    
    private BookStoreRepository bookStoreRepository;
    private AuthorRepository authorRepository;

    public BookStoreServiceImpl(BookStoreRepository bookStoreRepository, AuthorRepository authorRepository) {
        this.bookStoreRepository = bookStoreRepository;
        this.authorRepository = authorRepository;
    }

    // add book
    @Override
    public BookResponse addBook(BookEntity bookEntity) {
        String message = null;
        String status = HttpStatus.OK.toString();
        List<BookEntity> bookEntityList = null;
        List<AuthorEntity> authors = null;
        // validate book fields
        boolean failedValidation = bookEntity == null ||
                                   bookEntity.getTitle() == null || bookEntity.getTitle().isBlank() ||
                                   bookEntity.getPrice() < 0 ||
                                   bookEntity.getGenre() == null || bookEntity.getGenre().isBlank() ||
                                   bookEntity.getPublishedYear() < 0 || bookEntity.getPublishedYear() > Year.now().getValue();

        // validate author fields
        failedValidation = bookEntity.getAuthors() == null || bookEntity.getAuthors().isEmpty();
        if(!failedValidation) {
            authors = bookEntity.getAuthors();
            for(AuthorEntity author : authors) {
               failedValidation = author.getBirthDay() == null || author.getBirthDay().isBlank() ||
                                  author.getName() == null || author.getName().isBlank();

                // validate author dob
                if(!failedValidation) {
                    try {
                        LocalDate.parse(author.getBirthDay(), DATE_FORMATTER);
                    } catch (DateTimeParseException dtpe) {
                        failedValidation = true;
                    }   
                    
                }
            }
        }

        if(!failedValidation) {
            try {
                for(AuthorEntity author : authors) {
                    author.setBookEntity(bookEntity);
                }
                BookEntity savedBook = bookStoreRepository.save(bookEntity);
                authorRepository.saveAll(authors);
                bookEntityList = new ArrayList<BookEntity>();
                bookEntityList.add(savedBook);
                message = "New book has been added successfully";
            } catch (Exception e) {
                log.error(e.getMessage());
                message = "Please contact system admin";
                status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
            }
        } else {
            message = "Failed to add new book as the request format is invalid";
            status = HttpStatus.BAD_REQUEST.toString();
        }
        
        return new BookResponse(status,message,bookEntityList);
    }

    // find book
    @Override
    public BookResponse findBooks(String title, List<String> authors) {
        List<BookEntity> bookEntityList = null;
        String message = null;
        String status = HttpStatus.OK.toString();

        try{
            // by only title
            if((title != null && !title.isBlank()) && (authors == null || authors.isEmpty())) 
                bookEntityList = bookStoreRepository.findByTitle(title);
            // by only author
            else if((authors != null && !authors.isEmpty()) && (title == null || title.isBlank())) 
                bookEntityList = bookStoreRepository.findByAuthors(authors);
            // by author and title
            else if(title != null && !title.isBlank() && authors != null && !authors.isEmpty())
                bookEntityList = bookStoreRepository.findByTitleAndAuthors(title, authors);
            
            if(!bookEntityList.isEmpty()) {
                message = "Books found";
            } else {
                status = HttpStatus.NOT_FOUND.toString();
                message = "No books were found matching the search criteria";
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            message = "Please contact system admin";
            status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
        
            
        return new BookResponse(status, message, bookEntityList);
    }

    @Override
    public BookResponse updateBookByIsbn(BookEntity bookEntity, String isbn) {
        BookEntity updatedBook = null;
        List<BookEntity> bookEntityList = null;
        List<AuthorEntity> authors = null;
        String message = null;
        String status = HttpStatus.OK.toString();

        // validate book fields
        boolean failedValidation = bookEntity == null ||
                                   bookEntity.getTitle() == null || bookEntity.getTitle().isBlank() ||
                                   bookEntity.getPrice() < 0 ||
                                   bookEntity.getGenre() == null || bookEntity.getGenre().isBlank() ||
                                   bookEntity.getPublishedYear() < 0 || bookEntity.getPublishedYear() > Year.now().getValue();

        // validate author fields
        failedValidation = bookEntity.getAuthors() == null || bookEntity.getAuthors().isEmpty();
        if(!failedValidation) {
            authors = bookEntity.getAuthors();
            for(AuthorEntity author : authors) {
               failedValidation = author.getBirthDay() == null || author.getBirthDay().isBlank() ||
                                  author.getName() == null || author.getName().isBlank();

                // validate author dob
                if(!failedValidation) {
                    try {
                        LocalDate.parse(author.getBirthDay(), DATE_FORMATTER);
                    } catch (DateTimeParseException dtpe) {
                        failedValidation = true;
                    }   
                    
                }
            }
        }

        if(!failedValidation) {
            try {
                Optional<BookEntity> oldBookOptional = bookStoreRepository.findById(isbn);
                if(oldBookOptional.isPresent()) {
                    BookEntity oldBook = oldBookOptional.get();
                    oldBook.setTitle(bookEntity.getTitle());
                    oldBook.setGenre(bookEntity.getGenre());
                    oldBook.setPublishedYear(bookEntity.getPublishedYear());
                    oldBook.setPrice(bookEntity.getPrice());
                    for(AuthorEntity author : authors) {
                        author.setBookEntity(oldBook);
                    }
                    oldBook.setAuthors(authors);
                    updatedBook = bookStoreRepository.save(oldBook);
                    authorRepository.saveAll(authors);
                    bookEntityList = new ArrayList<BookEntity>();
                    bookEntityList.add(updatedBook);
                    message = "Book updated successfully";
                } else {
                    status = HttpStatus.NOT_FOUND.toString();
                    message = String.format("No book with isbn: %s was found", isbn);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                message = "Please contact system admin";
                status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
            }
        } else {
            message = String.format("Failed to update book with isbn : %s as the request format is invalid", isbn);
            status = HttpStatus.BAD_REQUEST.toString();
        }
        
        
        return new BookResponse(status, message, bookEntityList);
    }

    @Override
    public BookResponse deleteBookByIsbn(String isbn) {
        String message = null;
        String status = HttpStatus.OK.toString();
        List<BookEntity> bookEntityList = null;
        try{
            Optional<BookEntity> bookEntityOptional = bookStoreRepository.findById(isbn);
            if(bookEntityOptional.isPresent()) {
                BookEntity deletedBook = bookEntityOptional.get();
                bookStoreRepository.deleteById(isbn);
                bookEntityList = new ArrayList<BookEntity>();
                bookEntityList.add(deletedBook);
                message = "Book has been deleted successfully";
            } else {
                status = HttpStatus.NOT_FOUND.toString();
                message = String.format("No book with isbn: %s was found", isbn);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            message = "Please contact system admin";
            status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        }
        
        return new BookResponse(status, message, bookEntityList);
    }
}
