package com.bookstore.bookstore_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.bookstore_manager.Entity.BookEntity;

@Repository
public interface BookStoreRepository extends CrudRepository<BookEntity, String> {

    public List<BookEntity> findByTitle(String title);

    @Query("SELECT b FROM BOOK b JOIN b.authors a WHERE a.name IN :authors")
    public List<BookEntity> findByAuthors(@Param("authors") List<String> authors);
    
    @Query("SELECT b FROM BOOK b JOIN b.authors a WHERE a.name IN :authors AND b.title = :title")
    public List<BookEntity> findByTitleAndAuthors(@Param("title") String title, @Param("authors") List<String> authors);
}
