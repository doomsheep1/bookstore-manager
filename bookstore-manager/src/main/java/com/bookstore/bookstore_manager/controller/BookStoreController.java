package com.bookstore.bookstore_manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.bookstore.bookstore_manager.Entity.BookEntity;
import com.bookstore.bookstore_manager.Entity.BookResponse;
import com.bookstore.bookstore_manager.service.BookStoreService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class BookStoreController {

    private BookStoreService bookStoreService;

    public BookStoreController (BookStoreService bookStoreService) {
        this.bookStoreService = bookStoreService;
    }

    @PostMapping("/find-book")
    public BookResponse findBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) List<String> authors) {
        return bookStoreService.findBooks(title, authors);
    }

    @PostMapping("/add-book")
    public BookResponse addBook(@RequestBody BookEntity bookEntity) {
        return bookStoreService.addBook(bookEntity);
    }

    @PutMapping("/update-book/{isbn}")
    public BookResponse updateBook(@RequestBody BookEntity entity, @PathVariable String isbn) {
        return bookStoreService.updateBookByIsbn(entity, isbn);
    }

    @DeleteMapping("delete-book")
    public BookResponse deleteBookByIsbn(String isbn) {
        return bookStoreService.deleteBookByIsbn(isbn);
    }
    
}
