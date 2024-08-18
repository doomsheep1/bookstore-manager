package com.bookstore.bookstore_manager.Entity;

import java.util.List;

public record BookResponse(String status, String message, List<BookEntity> bookEntityList) {}
