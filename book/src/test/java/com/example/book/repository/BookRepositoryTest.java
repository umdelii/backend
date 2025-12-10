package com.example.book.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.entity.QBook;

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

    @Test
    public void testFindBy() {
        List<Book> list = bookRepository.findByAuthor("정이안");
        System.out.println("findByAuthor 실행 결과" + list);

        list = bookRepository.findByAuthorEndingWith("안");
        System.out.println("findByAuthor 실행 결과" + list);

        list = bookRepository.findByAuthorStartingWith("김");
        System.out.println("findByAuthorStartingWith 실행 결과" + list);

        list = bookRepository.findByAuthorContaining("하람");
        System.out.println("findByAuthorContaining 실행 결과" + list);

        list = bookRepository.findByPriceBetween(70000L, 79000L);
        System.out.println("findByPriceBetween 실행 결과" + list);
    }

    @Test
    public void pageTest() {
        // 페이지 나누기
        // bookRepository.findAll(Pageable pageable);
        // 0으로 시작
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(pageRequest);
        // 설정한 페이즈 사이즈(한 페이즈에 몇개 들어가있는지)
        System.out.println("page size" + result.getSize());
        // 총 페이지 수
        System.out.println("total pages" + result.getTotalPages());
        // 총 행의 개수
        System.out.println("TotalElements" + result.getTotalElements());
        System.out.println("getContent" + result.getContent());

    }

    // --------------------------------------
    // querydsl 라이브러리 추가 // querydslpredicateexecutor
    @Test
    public void querydslTest() {
        QBook book = QBook.book;
        System.out.println(bookRepository.findAll(book.title.eq("title1"))); // equal => where title = ?
        System.out.println(bookRepository.findAll(book.title.contains("10"))); // like %10%
        System.out.println(bookRepository.findAll(book.title.contains("10").and(book.id.gt(3L)))); // like %10% and id>3

        // where b1_0.title like %?% and b1_0.id > ? order by id desc
        System.out.println(bookRepository.findAll(book.title.contains("book").and(book.id.gt(3L)),
                Sort.by("id").descending()));

        // where author '%정%' or title = '%8'
        System.out.println(bookRepository.findAll(book.title.contains("정").or(book.author.contains("8"))));

        // bookRepository.findAll(Predicate predicate, Pageable pageable);
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Book> result = bookRepository.findAll(book.id.gt(200L), pageRequest);

    }

}
