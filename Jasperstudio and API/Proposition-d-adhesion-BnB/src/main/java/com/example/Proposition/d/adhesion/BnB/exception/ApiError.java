package com.example.Proposition.d.adhesion.BnB.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Réponse d'erreur standard de l'API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorDto> fieldErrors;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldErrorDto {
        private String field;
        private String message;
    }
}
