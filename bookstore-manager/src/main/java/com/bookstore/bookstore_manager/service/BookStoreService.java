package com.bookstore.bookstore_manager.service;

import java.util.List;

import com.bookstore.bookstore_manager.Entity.BookEntity;
import com.bookstore.bookstore_manager.Entity.BookResponse;

public interface BookStoreService {
    BookResponse addBook(BookEntity bookEntity);
    BookResponse findBooks(String title, List<String> authors);
    BookResponse updateBookByIsbn(BookEntity bookEntity, String isbn);
    BookResponse deleteBookByIsbn(String isbn);
}
