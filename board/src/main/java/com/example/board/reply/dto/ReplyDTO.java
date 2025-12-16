package com.example.board.reply.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long bno;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

}
