package com.example.ai.service;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import com.example.ai.domain.request.AdCopyRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

        private final ChatClient chatClient;
        private final EmbeddingModel embeddingModel;

        public String exam1(String userInput, String systemMessage) {
                return chatClient.prompt().user(userInput).system(systemMessage).call().content();
        }

        public ChatResponse exam2(String userInput, String systemMessage) {
                return chatClient.prompt().user(userInput).system(systemMessage).call().chatResponse();
        }

        public String exam4(String userInput, String conversionId) {
                ChatOptions chatOptions = ChatOptions.builder().temperature(1.0).model("gpt-5-nano").build();
                Prompt prompt = Prompt.builder().chatOptions(chatOptions)
                                .messages(UserMessage.builder().text(userInput).build()).build();

                return chatClient.prompt(prompt).advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversionId))
                                .call().content();
        }

        // prompt() 사용법
        public ChatResponse exam3(String userInput, String systemMessage, String model) {

                // openai chat completion 모델에서 사용하는 모든 옵션 지정가능
                ChatOptions chatOptions = ChatOptions.builder().model(model).temperature(1.0).build();

                Prompt prompt = Prompt.builder()
                                .messages(SystemMessage.builder().text(systemMessage).build(),
                                                UserMessage.builder().text(userInput).build())
                                .chatOptions(chatOptions)
                                .build();

                return chatClient.prompt(prompt).call().chatResponse();
        }

        public String copyWriter(AdCopyRequest adCopyRequest) {
                ChatOptions chatOptions = ChatOptions.builder().temperature(1.0).model("gpt-5-nano").build();

                Prompt prompt = Prompt.builder()
                                .messages(UserMessage.builder()
                                                .text(PromptFactory.render(
                                                                adCopyRequest.name(), adCopyRequest.brandName(),
                                                                adCopyRequest.strength(), adCopyRequest.toneManner(),
                                                                adCopyRequest.value(), adCopyRequest.keyword()))
                                                .build())
                                .chatOptions(chatOptions)
                                .build();

                return chatClient.prompt(prompt).call().content();
        }

        public EmbeddingResponse embed(String message) {
                EmbeddingResponse embeddingResponse = embeddingModel.embedForResponse(List.of(message));
                return embeddingResponse;
        }
}
