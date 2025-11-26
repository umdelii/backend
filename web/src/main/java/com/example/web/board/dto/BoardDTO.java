package com.example.web.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private long id;
    private String title;
    private String writer;
    private LocalDateTime regDate;
}
