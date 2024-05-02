package edu.java.bot.exception;

import edu.java.bot.dto.api.ApiErrorResponse;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
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
