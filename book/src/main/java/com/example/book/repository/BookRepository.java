package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.book.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(String isbn); // "="의 의미만 가지고있음 즉, where isbn = "B0001", like 개념이 없음

    List<Book> findByTitleContaining(String title); // select * from book where title like '%book%'; 의 의미

}
