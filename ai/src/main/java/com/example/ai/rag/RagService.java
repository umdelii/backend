package com.example.ai.rag;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class RagService {
    private final DocumentProcessingService documentProcessingService;
    private final InMemoryDocumentVectorStore vectorStore;
    private final ChatClient chatClient;

    public String uploadPdfFile(File file, String oriFileName) {
        var docId = UUID.randomUUID().toString();

        log.info("PDF 업로드 파일 {}, {}", oriFileName, docId);

        var docMetadata = new HashMap<String, Object>();
        docMetadata.put("originalFileName", oriFileName);
        docMetadata.put("uploadTime", System.currentTimeMillis());

        try {
            addDocumentFile(docId, file, docMetadata);
            log.info("pdf 파일 업로드 완료 ID : {}", docId);
            return docId;
        } catch (Exception e) {
            log.error("업로드 오류 발생");
            throw new DocumentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    private void addDocumentFile(String docId, File file, Map<String, Object> metadata) {
        log.info("파일 문서 추가 {}, {}", docId, file.getName());

        try {
            String extension = getExtensionLower(file.getName());
            String fileText = null;
            if ("pdf".equals(extension)) {
                fileText = documentProcessingService.extractTextFormPdf(file);
            } else {
                fileText = Files.readString(file.toPath());
            }
            vectorStore.addDocument(docId, fileText, metadata);
            log.debug("파일 텍스트 추출 완료 - 길이 {}", fileText.length());
        } catch (Exception e) {
            throw new DocumentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR, "텍스트 추출 실패", e);
        }
    }

    private String getExtensionLower(String fileName) {
        int idx = fileName.lastIndexOf(".");
        if (idx < 0 || idx == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(idx + 1).toLowerCase();
    }

    // 검색
    public List<DocumentSearchResultDTO> retrieve(String query, int maxResults) {
        log.debug("검색 시작 {}, 최대 결과 수 {}", query, maxResults);
        return vectorStore.similaritySearch(query, maxResults);
    }

    public String generateAnswerWithContexts(String query, List<DocumentSearchResultDTO> reDocs, String conversionId) {
        if (reDocs.isEmpty()) {
            log.info("관련 문서 없음 " + query);
            return "관련 문서를 찾을 수 없습니다.";
        }

        List<String> numberedDocs = IntStream.range(0, reDocs.size())
                .mapToObj(i -> "[" + (i + 1) + "]" + reDocs.get(i).getContent())
                .collect(Collectors.toList());

        String context = String.join("\n\n", numberedDocs);
        log.debug("context 크기 : {}", context.length());

        String systemPrompt = """
                당신은 지식 기반 Q&A 시스템입니다.
                사용자의 질문에 대한 답변을 다음 정보를 기반으로 생성해주세요.
                주어진 정보다 답이 없다면 절대 모른다고 답해주세요.
                답변 마지막에 사용한 정보의 출처 번호 [1], [2] 같이 표기해주세요.

                정보:
                    %s
                """.formatted(context);

        String answer = chatClient.prompt()
                .system(systemPrompt)
                .user(query).advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversionId))
                .call().content();

        return answer;
    }
}
