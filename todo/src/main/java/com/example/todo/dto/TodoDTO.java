package com.example.todo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {
    private Long id;

    private String title;

    private boolean completed;

    private boolean important;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
