package com.example.memo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoDTO {
    private Long mno;
    private String menoText;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
