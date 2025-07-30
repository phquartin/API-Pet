package dev.phquartin.pets.config;

import dev.phquartin.pets.exception.ErrorResponse;
import dev.phquartin.pets.exception.NoDataFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss | dd/MM/yyyy");

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage() + " | Class name Exception: " + e.getClass().getSimpleName())
                .status(500)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return ResponseEntity.internalServerError().body(errorResponse);
    }

    // Quando a pagina nao for encontrada (Nao existir uma rota)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(HttpServletRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Page not found")
                .status(404)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Manipulador para exceções de validação (@Valid).
     * Retorna um corpo de resposta com os erros específicos de cada campo.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        String errorMessage = e.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(" | "));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errorMessage)
                .status(400)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Manipulador para exceções de validação na @Entity.
     * Retorna um corpo de resposta com os erros específicos de cada campo.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(" | "));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errorMessage)
                .status(400)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoDataFoundException(NoDataFoundException e, HttpServletRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(e.getMessage())
                .status(404)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(formatter))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
