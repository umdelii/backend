package com.example.jpa.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @Test
    public void testInsert() {
        Parent parent = Parent.builder().name("parent1").build();
        parentRepository.save(parent);

        IntStream.rangeClosed(1, 3).forEach(i -> {
            Child child = Child.builder().name("child" + i).parent(parent).build();
            childRepository.save(child);
        });
    }
}
