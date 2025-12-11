package com.example.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.book.entity.Book;
import com.example.book.entity.QBook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
    Optional<Book> findByIsbn(String isbn); // "="의 의미만 가지고있음 즉, where isbn = "B0001", like 개념이 없음

    List<Book> findByTitleContaining(String title); // select * from book where title like '%book%'; 의 의미

    // where author = ''; 아니면
    List<Book> findByAuthor(String author);

    // where author like '%영'
    List<Book> findByAuthorEndingWith(String author);

    // where author like '박%'
    List<Book> findByAuthorStartingWith(String author);

    // where author like '%진수%'
    List<Book> findByAuthorContaining(String author);

    // 도서 가격이 70000이상 79000이하
    List<Book> findByPriceBetween(Long startPrice, Long endPrice);

    public default Predicate makePredicate(String type, String keyword) {
        BooleanBuilder builder = new BooleanBuilder();
        QBook book = QBook.book;

        builder.and(book.id.gt(0));

        if (type == null) {
            return builder;
        }

        // 저자나 제목으로 검색하기
        // type == 't'(title), type == 'a'(author)
        if (type.equals("t")) {
            // title like '%keyword%'
            builder.and(book.title.contains(keyword));
        } else {
            // author like '%keyword'
            builder.and(book.author.contains(keyword));
        }
        return builder;
    }
}
