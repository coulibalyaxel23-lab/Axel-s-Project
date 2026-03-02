package com.example.Proposition.d.adhesion.BnB.exception;

/**
 * Exception levée lorsqu'une erreur survient lors de la génération d'un rapport.
 */
public class ReportGenerationException extends RuntimeException {

    public ReportGenerationException(String message) {
        super(message);
    }

    public ReportGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
