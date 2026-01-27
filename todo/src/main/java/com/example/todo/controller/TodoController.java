package com.example.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.dto.PageRequestDTO;
import com.example.todo.dto.PageResultDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/todos")
@Log4j2
@RequiredArgsConstructor
@Tag(name = "Response Todos", description = "Response Todo API")
public class TodoController {
    private final TodoService todoService;

    // 전체조회 http://localhost:8080/todos?completed=
    // 완료조회 http://localhost:8080/todos?completed=true
    // 미완료조회 http://localhost:8080/todos?completed=false
    @GetMapping("")
    @Operation(summary = "todo 照会", description = "Todo 照会 API - ?completed")
    @CrossOrigin(origins = "http://localhost:5173") // 컨트롤러 단위 cors 설정 , 개별설정
    public PageResultDTO<TodoDTO> getTodos(@RequestParam(required = false) Boolean completed, PageRequestDTO dto) {
        log.info("dto list 要請");

        return todoService.findCompletedTodos(completed, dto);
    }

    // 입력 http://localhost:8080/todos/add
    @PostMapping("/add")
    @Operation(summary = "todo 登録", description = "Toto 追加 API")
    public Long postTodo(@RequestBody TodoDTO dto) {
        log.info("create todo {}", dto);
        Long id = todoService.create(dto);

        return id;
    }

    // 수정 http://localhost:8080/todos/1
    @PutMapping("/{id}")
    @Operation(summary = "todo 変更", description = "Toto 修正 API")
    public Long putMethodName(
            @Parameter(description = "id値", example = "1", required = true) @PathVariable Long id,
            @RequestBody TodoDTO dto) {
        log.info("put todo, {}, {}", id, dto);
        dto.setId(id);
        return todoService.update(dto);
    }

    // 삭제 http://localhost:8080/todos/1
    @DeleteMapping("/{id}")
    @Operation(summary = "todo 削除", description = "Toto 削除 API")
    public ResponseEntity<String> deleteTodo(
            @Parameter(description = "delete todo id値", example = "1", required = true) @PathVariable Long id) {
        log.info("delete todo id {}", id);
        todoService.delete(id);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
