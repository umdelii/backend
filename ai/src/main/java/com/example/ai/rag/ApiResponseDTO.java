package com.example.ai.rag;

// 응답 객체 - 성공과 실패를 구분하기 위한 DTO
public record ApiResponseDTO<T>(T data, boolean success, String errorMsg) {
    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<T>(data, true, "성공");
    }

    public static <T> ApiResponseDTO<T> failure(String errorMsg) {
        return new ApiResponseDTO<T>(null, false, errorMsg);
    }
}
