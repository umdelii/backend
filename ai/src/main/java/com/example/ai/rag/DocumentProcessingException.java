package com.example.ai.rag;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

// 자체 exception 클래스 작성
public class DocumentProcessingException extends ResponseStatusException {

    public DocumentProcessingException(HttpStatusCode status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

}
