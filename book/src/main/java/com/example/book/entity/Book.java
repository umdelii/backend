package com.example.book.entity;

import groovyjarjarantlr4.v4.parse.BlockSetTransformer.setAlt_return;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String author;

    // 작업 중 컬럼 추가를 받을때
    private String description;

    public void changePrice(long price) {
        this.price = price;
    }

    public void changeIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void changeDescription(String description) {
        this.description = description;
    }
}
