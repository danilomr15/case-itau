package br.com.danilomr.cliente.exception;

import br.com.danilomr.cliente.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR = "Error occurred while trying to call the API=[%s]";

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorDto> handle(final HttpClientErrorException ex, final HttpServletRequest request) {

        return buildErrorDto(ex.getStatusCode().value(), ex.getStatusText(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handle(final MethodArgumentNotValidException ex, final HttpServletRequest request) {

        final String error = ex.getBindingResult().getAllErrors().stream()
                .filter(Objects::nonNull)
                .findFirst()
                .map(ObjectError::getDefaultMessage)
                .orElse(String.format(DEFAULT_ERROR, request.getRequestURI()));

        return buildErrorDto(ex.getStatusCode().value(), error, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handle(final HttpMessageNotReadableException ex, final HttpServletRequest request) {

        return buildErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request);
    }

    private ResponseEntity<ErrorDto> buildErrorDto(final int statusCode, final String error, final HttpServletRequest request) {

        final ErrorDto errorDto = ErrorDto.builder()
                .error(error)
                .status(statusCode)
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .method(request.getMethod())
                .build();
        return ResponseEntity.status(errorDto.getStatus()).body(errorDto);
    }
}
