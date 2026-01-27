package com.example.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.todo.dto.PageRequestDTO;
import com.example.todo.dto.PageResultDTO;
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

    public PageResultDTO<TodoDTO> findCompletedTodos(Boolean completed, PageRequestDTO dto) {

        // page 시작은 0으로 했었으나 react 페이지 나누기 라이브러리 사용하면 0으로 넣어줌(-1을 화면에서 처리해줌)
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("id").descending());

        Page<Todo> result = null;

        if (completed == null) {
            result = todoRepository.findAll(pageable);
        } else {
            result = todoRepository.findByCompleted(completed, pageable);
        }

        List<TodoDTO> dtoList = result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

        return PageResultDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .totalCount(result.getTotalElements())
                .pageRequestDTO(dto)
                .build();
    }

    public List<TodoDTO> findImportantTodos(boolean important) {
        List<Todo> result = todoRepository.findByImportant(important);
        return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
                .collect(Collectors.toList());

    }

    // public List<TodoDTO> findTodos() {
    // List<Todo> result = todoRepository.findAll();
    // return result.stream().map(todo -> modelMapper.map(todo, TodoDTO.class))
    // .collect(Collectors.toList());

    // }
}
