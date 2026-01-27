package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.entity.Todo;

@SpringBootTest
@Transactional
public class TodoRepositoryTest {
    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Commit
    public void insertTest() {
        IntStream.rangeClosed(35, 141).forEach(i -> {
            Todo todo = Todo.builder()
                    .title("今日やること " + i)
                    .completed(i % 2 == 0 ? true : false)
                    .important(i % 2 == 0 ? true : false)
                    .build();

            todoRepository.save(todo);
        });
    }

    @Test
    public void readAllTest() {
        todoRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void readByCompleted() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        todoRepository.findByCompleted(true, pageable).forEach(System.out::println);
    }
}
