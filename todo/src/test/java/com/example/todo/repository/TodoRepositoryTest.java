package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        IntStream.rangeClosed(1, 30).forEach(i -> {
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
}
