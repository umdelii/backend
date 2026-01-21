package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.entity.Todo;
import com.example.todo.repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    public Long create(TodoDTO dto) {
        Todo todo = modelMapper.map(dto, Todo.class);
        return todoRepository.save(todo).getId();
    }

    public Long update(TodoDTO dto) {
        Todo todo = todoRepository.findById(dto.getId()).orElseThrow(EntityNotFoundException::new);
        todo.setTitle(dto.getTitle());
        todo.setCompleted(dto.isCompleted());
        todo.setImportant(dto.isImportant());

        return todo.getId();
    }

    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    public List<TodoDTO> findCompletedTodos(boolean completed) {
        List<Todo> result = todoRepository.findByCompleted(completed);
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());
    }

    public List<TodoDTO> findImportantTodos(boolean important) {
        List<Todo> result = todoRepository.findByImportant(important);
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

    }

    public List<TodoDTO> findTodos() {
        List<Todo> result = todoRepository.findAll();
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

    }
}
