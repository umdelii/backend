package com.example.ai.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.api.OpenAiApi.EmbeddingModel;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ai.domain.request.AdCopyRequest;
import com.example.ai.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
@Tag(name = "OpenAI LLM", description = "OpenAI LLM TEST")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/chat1")
    @Operation(summary = "Chat Completion test", description = "Spring AI 활용 Chat Completion 기능 구현")
    public String getChat(String userInput, String systemMessage) {
        String result = chatService.exam1(userInput, systemMessage);
        return result;
    }

    @GetMapping("/chat2")
    @Operation(summary = "Chat Completion test", description = "Spring AI 활용 Chat Completion 기능 구현")
    public ChatResponse getChat2(String userInput, String systemMessage) {
        ChatResponse result = chatService.exam2(userInput, systemMessage);
        return result;
    }

    @GetMapping("/chat3")
    @Operation(summary = "Chat Completion test", description = "Spring AI 활용 Chat Completion 기능 구현")
    public ChatResponse getChat3(String userInput, String systemMessage, String model) {
        ChatResponse result = chatService.exam3(userInput, systemMessage, model);
        return result;
    }

    @GetMapping("/chat4")
    @Operation(summary = "Chat Completion test", description = "Spring AI 활용 Chat Completion 기능 구현")
    public String getChat4(String userInput, HttpSession httpSession) {
        log.info(userInput);

        String conversionId = httpSession.getId();
        String result = chatService.exam4(userInput, conversionId);
        return result;
    }

    @PostMapping("/copywriter")
    @Operation(summary = "Chat Completion test", description = "Spring AI 활용 Chat Completion 기능 구현")
    public String postCopyWriter(@RequestBody AdCopyRequest adCopyRequest) {
        log.info(adCopyRequest);
        String result = chatService.copyWriter(adCopyRequest);
        return result;
    }

    @GetMapping("/embedded")
    @Operation(summary = "Embedding test", description = "Spring AI 활용 Embedding 기능 구현")
    public Map<String, List<Embedding>> getEmbedded(String userInput) {
        log.info(userInput);
        EmbeddingResponse result = chatService.embed(userInput);
        return Map.of("embedding", result.getResults());
    }
}
