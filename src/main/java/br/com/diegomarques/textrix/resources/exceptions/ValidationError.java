package br.com.diegomarques.textrix.resources.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationError {
    private final StandardError standardError;
    private final List<FieldMessage> errors;

    public ValidationError(StandardError standardError) {
        this.standardError = standardError;
        this.errors = new ArrayList<>();
    }

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        this(new StandardError(timestamp, status, error, message, path));
    }

    public List<FieldMessage> getErrors() {
        return new ArrayList<>(errors);
    }

    public void addError(String fieldName, String message) {
        errors.add(new FieldMessage(fieldName, message));
    }

    public StandardError getStandardError() {
        return standardError;
    }
}
