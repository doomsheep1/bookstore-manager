package com.bookstore.bookstore_manager.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name="BOOK")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String isbn;


    private String title;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "bookEntity", cascade = CascadeType.ALL)
    private List<AuthorEntity> authors;

    private int publishedYear;


    private double price;


    private String genre;

    protected BookEntity() {}

    public BookEntity(String title, int publishedYear, double price, String genre) {
        this.title = title.trim();
        this.publishedYear = publishedYear;
        this.price = price;
        this.genre = genre.trim();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public List<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorEntity> authors) {
        this.authors = authors;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre.trim();
    }
    
    @Override
    public String toString() {
        return "BookEntity [isbn=" + isbn + ", title=" + title + ", publishedYear=" + publishedYear + ", price=" + price
                + ", genre=" + genre + "]";
    }

    
    
}
