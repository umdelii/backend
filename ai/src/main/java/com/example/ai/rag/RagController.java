package com.example.ai.rag;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequiredArgsConstructor
@Tag(name = "OpenAI LLM", description = "OpenAI TEST - RAG")
@Log4j2
public class RagController {
    private final RagService ragService;
    private final ChatClient chatClient;

    // 화면단 enctype = "multipart/form-data" 로 파일 업로드 요청이 들어옴
    // consumes = MediaType.MULTIPART_FORM_DATA_VALUE : 파일 업로드를 위한 요청임을 명시
    @PostMapping(value = "/document", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDTO<String>> handleFileUpload(MultipartFile file) {
        log.info("파일 업로드 {}", file.getOriginalFilename());

        // 파일 유효성 검사
        if (file.isEmpty()) {
            log.warn("업로드된 파일이 비어있음");
            return ResponseEntity.badRequest().body(ApiResponseDTO.failure("파일이 비어있습니다."));
        }

        // 지원하는 파일 형식 검사 (예: PDF만 허용)
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            log.warn("지원하지 않는 파일 형식 {}", file.getOriginalFilename());
            return ResponseEntity.badRequest().body(ApiResponseDTO.failure("지원하지 않는 파일 형식입니다."));
        }

        File tempFile = null;

        try {
            // 파일 저장
            tempFile = File.createTempFile("upload_", ".pdf");
            log.debug("임시 파일 저장 {}", tempFile.getAbsolutePath());
            file.transferTo(tempFile);
        } catch (Exception e) {
            log.warn("파일 저장 중 오류 발생");
            return ResponseEntity.status(500).body(ApiResponseDTO.failure("파일 저장 중 오류가 발생했습니다."));
        }

        try {
            // RAG 서비스 호출 - PDF 파일 처리 및 벡터 스토어에 저장
            String docId = ragService.uploadPdfFile(tempFile, file.getOriginalFilename());
            log.info("파일 업로드 성공 {}", docId);
            return ResponseEntity.ok(ApiResponseDTO.success(docId));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("파일 처리 중 오류 발생");
        } finally {
            // 임시 파일 삭제
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
                log.debug("임시 파일 삭제 {}", tempFile.getAbsolutePath());
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.failure("알 수 없는 오류 발생"));
    }

    @PostMapping("/api/v1/rag")
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> postQuery(@RequestBody QueryRequestDTO queryRequestDTO,
            @RequestHeader(value = "X-CHAT-ID", required = false) String chatId) {
        log.info("질문 요청 {}", queryRequestDTO);

        if (queryRequestDTO.getQuery().isEmpty()) {
            log.info("질문이 비어있음");
            return ResponseEntity.badRequest().body(ApiResponseDTO.failure("질문이 비어있습니다."));
        }

        String conversationId = (chatId == null || chatId.isBlank()) ? UUID.randomUUID().toString() : chatId;
        log.info("대화 ID {}", conversationId);

        List<DocumentSearchResultDTO> reDocs = ragService.retrieve(queryRequestDTO.getQuery(),
                queryRequestDTO.getMaxResult());
        String answer = ragService.generateAnswerWithContexts(queryRequestDTO.getQuery(), reDocs, conversationId);

        return ResponseEntity.ok()
                .body(ApiResponseDTO.success(Map.of("answer", answer, "conversationId", conversationId)));
    }

}