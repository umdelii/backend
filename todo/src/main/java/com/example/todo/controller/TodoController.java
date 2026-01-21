package com.example.todo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/todos")
@Log4j2
public class TodoController {

    // 전체조회 http://localhost:8080/todos
    // 완료조회 http://localhost:8080/todos?completed=ture
    // 입력 http://localhost:8080/todos/add
    // 수정 http://localhost:8080/todos/1
    // 삭제 http://localhost:8080/todos/1
}
