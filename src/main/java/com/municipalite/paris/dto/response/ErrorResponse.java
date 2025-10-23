package com.municipalite.paris.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private boolean error;
    private String message;
    private String code;
    private LocalDateTime timestamp;
    private Map<String, String> details;
    
    public static ErrorResponse of(String message) {
        return ErrorResponse.builder()
                .error(true)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static ErrorResponse of(String message, String code) {
        return ErrorResponse.builder()
                .error(true)
                .message(message)
                .code(code)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static ErrorResponse of(String message, String code, Map<String, String> details) {
        return ErrorResponse.builder()
                .error(true)
                .message(message)
                .code(code)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
    }
}


