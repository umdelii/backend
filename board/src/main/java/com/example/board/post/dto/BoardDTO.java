package com.example.board.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDTO {
    private Long bno;

    private String title;

    private String content;

    // service에 사용해야할 메소드에 필요한 필드들
    private String writerEmail; // 작성자 이메일
    private String writerName; // 작성자 이름
    private int replyCnt;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
}
