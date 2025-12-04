package com.example.book.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@Disabled
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper mapper;

    @Test
    public void testInsert() {
        BookDTO book = BookDTO.builder()
                .isbn("B10011")
                .title("book1")
                .price(80000L)
                .author("정이안")
                .build();
        bookRepository.save(mapper.map(book, Book.class));
    }

    @Test
    public void testInsert2() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Book book = Book.builder()
                    .isbn("B1000" + i)
                    .title("book" + i)
                    .price(80000L)
                    .author("정이안")
                    .build();

            bookRepository.save(book);
        });
    }

    @Test
    public void testRead() {
        // bookRepository.findById(1L).orElseThrow(EntityNotFoundException::new);
        Book book = bookRepository.findById(1L).orElseThrow();
        System.out.println(book);
    }

    // findByIsbn
    @Test
    public void testRead2() {
        Book book = bookRepository.findByIsbn("B10001").orElseThrow(EntityNotFoundException::new);
        System.out.println(book);

        List<Book> list = bookRepository.findByTitleContaining("3");
        System.out.println(list);
    }

    @Test
    public void testUpdate() {
        Book book = bookRepository.findById(2L).orElseThrow();
        book.changePrice(45000L);
        bookRepository.save(book);
    }

    @Test
    public void testUpdateIsbn() {
        Book book = bookRepository.findById(10L).orElseThrow();
        book.changeIsbn("B10010");
        bookRepository.save(book);
    }

    @Test
    public void testDelete() {
        bookRepository.deleteById(10L);
    }
}
