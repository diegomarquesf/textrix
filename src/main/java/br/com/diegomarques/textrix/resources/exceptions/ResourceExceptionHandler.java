package br.com.diegomarques.textrix.resources.exceptions;

import br.com.diegomarques.textrix.services.exceptions.DataIntegrityException;
import br.com.diegomarques.textrix.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> handleObjectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        return handleExceptionInternal(e, HttpStatus.NOT_FOUND, "Não Encontrado", request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> handleDataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        return handleExceptionInternal(e, HttpStatus.BAD_REQUEST, "Integridade de Dados", request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError error = createValidationError("Erro de Validação", request.getRequestURI());
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            if (objectError instanceof FieldError fieldError) {
                error.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
        });
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    private ResponseEntity<StandardError> handleExceptionInternal(RuntimeException ex, HttpStatus status, String error, String path) {
        StandardError standardError = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), path);
        return ResponseEntity.status(status).body(standardError);
    }

    private ValidationError createValidationError(String error, String path) {
        return new ValidationError(
                Instant.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                error,
                "Os dados fornecidos não são válidos. Corrija os erros e tente novamente.",
                path
        );
    }

    private StandardError createStandardError(HttpStatus status, String error, String message, String path) {
        return new StandardError(Instant.now(), status.value(), error, message, path);
    }

}
