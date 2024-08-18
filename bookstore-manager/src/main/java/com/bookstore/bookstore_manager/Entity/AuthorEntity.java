package com.bookstore.bookstore_manager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name="Author")
public class AuthorEntity {

    @ManyToOne
    @JoinColumn(name="isbn", nullable = false)
    @JsonIgnore
    private BookEntity bookEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Nonnull
    private String name;

    @Nonnull
    private String birthDay;

    protected AuthorEntity(){}

    public AuthorEntity(String name, String birthDay) {
        this.name = name.trim();
        this.birthDay = birthDay.trim();
    }

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay.trim();
    }

    @Override
    public String toString() {
        return "AuthorEntity [id=" + id + ", name=" + name + ", birthDay=" + birthDay + "]";
    }

}
