package com.example.ai.rag;

import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DocumentSearchResultDTO {

    @Schema(description = "문서 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "문서 내용")
    private String content;

    @Schema(description = "문서 메타데이터")
    private Map<String, Object> metadata;

    @Schema(description = "유사도 점수", example = "0.85")
    private double score;
}
