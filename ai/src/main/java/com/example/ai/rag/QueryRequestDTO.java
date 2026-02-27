package com.example.ai.rag;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QueryRequestDTO {
    @Schema(description = "사용자 질문", example = "이 문서의 주요 내용은 무엇인가요?")
    private String query;

    @Schema(description = "최대 결과 수", example = "3", defaultValue = "3")
    private Integer maxResult = 3;

    @Schema(description = "모델 이름", example = "gpt-5-nano", defaultValue = "gpt-5-nano")
    private String model = "gpt-5-nano";
}
