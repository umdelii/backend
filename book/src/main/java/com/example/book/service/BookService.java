package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    // crud 메소드를 호출하는 서비스메소드 작성

    public void create(BookDTO dto) {
        // dto -> entity 변경
        // 1.직접 코드 작성
        // 2.ModelMapper 라이브러리 사용
        Book book = modelMapper.map(dto, Book.class);
        bookRepository.save(book);
    }

    // R에 검색의 기능을 넣어보자
    // title로 검색하기 (unique니 검색은 하나밖에 되지않음)
    @Transactional(readOnly = true)
    public List<BookDTO> readTitle(String title) {
        List<Book> result = bookRepository.findByTitleContaining(title);

        // List<Book> => List<BookDTO>로 변경
        // List<BookDTO> list = new ArrayList<>();
        // result.forEach(book -> {
        // list.add(modelMapper.map(result, BookDTO.class));
        // });

        // 오전에 배운 스트림을 활용해서 코드를 간?단하게 만들어보자

        // 내가 짠거 (틀린듯)
        // List<BookDTO> list = Stream.of(modelMapper.map(result,
        // BookDTO.class)).collect(Collectors.toList());
        // System.out.println(list);

        return result.stream().map(book -> modelMapper.map(result, BookDTO.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDTO readIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow();

        // Optional<Book> => Optional<BookDTO> 변환
        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional(readOnly = true)
    public BookDTO readId(Long id) {
        Book book = bookRepository.findById(id).orElseThrow();

        return modelMapper.map(book, BookDTO.class);
    }

    @Transactional(readOnly = true)
    public PageResultDTO<BookDTO> readAll(PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("id").descending());

        Page<Book> result = bookRepository.findAll(bookRepository.makePredicate(null, null), pageable);

        // List<Book> book = bookRepository.findAll();
        List<BookDTO> dtoList = result.get().map(b -> modelMapper.map(b, BookDTO.class)).collect(Collectors.toList());
        // 전체 행의 개수
        long totalCount = result.getTotalElements();

        return PageResultDTO.<BookDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
    }

    public Long update(BookDTO dto) {
        Book book = bookRepository.findById(dto.getId()).orElseThrow();
        book.changePrice(dto.getPrice());
        book.changeDescription(dto.getDescription());

        // return bookRepository.save(book).getId();
        return book.getId();
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
