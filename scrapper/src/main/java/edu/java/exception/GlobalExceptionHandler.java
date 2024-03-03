package edu.java.exception;

import edu.java.dto.api.response.ApiErrorResponse;
import edu.java.exception.custom.ChatIdNotFoundException;
import edu.java.exception.custom.CustomApiException;
import edu.java.exception.custom.LinkNotFoundException;
import edu.java.exception.custom.ReAddingLinkException;
import edu.java.exception.custom.ReRegistrationException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ChatIdNotFoundException.class, LinkNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleNotFoundException(CustomApiException e) {
        return new ApiErrorResponse(
            e.getDescription(),
            e.getHttpStatus().toString(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList())
        );
    }

    @ExceptionHandler({ReAddingLinkException.class, ReRegistrationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiErrorResponse handleConflictException(CustomApiException e) {
        return new ApiErrorResponse(
            e.getDescription(),
            e.getHttpStatus().toString(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList())
        );
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequestException(MethodArgumentNotValidException e) {
        return new ApiErrorResponse(
            "Некорректные параметры запроса",
            HttpStatus.BAD_REQUEST.toString(),
            e.getClass().getSimpleName(),
            e.getMessage(),
            Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::toString)
                .collect(Collectors.toList())
        );
    }
}
