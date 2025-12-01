package com.example.memo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Cannot allow blank")
    private String memoText;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
