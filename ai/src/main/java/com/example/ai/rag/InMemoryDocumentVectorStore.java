package com.example.ai.rag;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Repository
@RequiredArgsConstructor
@Log4j2
public class InMemoryDocumentVectorStore {
    private final VectorStore vectorStore;

    public void addDocument(String docId, String fileText, Map<String, Object> metadata) {
        log.info("문서 추가 {}, {}", docId, fileText);

        try {
            Map<String, Object> merged = new HashMap<>();

            if (metadata != null) {
                merged.putAll(metadata);
            }

            var document = new Document(fileText, merged);

            // 데이터 분할
            var textsplitter = TokenTextSplitter.builder()
                    .withChunkSize(512)
                    .withMinChunkSizeChars(350)
                    .withMinChunkLengthToEmbed(5)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();

            var chunks = textsplitter.split(document);
            vectorStore.add(chunks); // 임베딩 변환하며 스토어에 추가

        } catch (Exception e) {
            throw new DocumentProcessingException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "fail to embedding or save: " + e.getMessage(), e);
        }
    }

    // vectorStore에서 찾기
    public List<DocumentSearchResultDTO> similaritySearch(String query, int maxResults) {
        var regRequest = SearchRequest.builder()
                .query(query)
                .topK(maxResults)
                .similarityThreshold(0.3)
                .build();

        List<Document> results = vectorStore.similaritySearch(regRequest);
        if (results == null) {
            return Collections.emptyList();
        }

        return results.stream()
                .map(r -> {
                    Map<String, Object> md = r.getMetadata();
                    String id = String.valueOf(md.getOrDefault("id", md.getOrDefault("docId", "unknown")));
                    String content = r.getText() == null ? "" : r.getText();
                    double score = r.getScore() == null ? 0.0 : r.getScore();

                    Map<String, Object> filtered = md.entrySet().stream()
                            .filter(e -> !e.getKey().equals("id") && !e.getKey().equals("docId"))
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

                    return DocumentSearchResultDTO.builder()
                            .id(id)
                            .content(content)
                            .metadata(filtered)
                            .score(score)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
