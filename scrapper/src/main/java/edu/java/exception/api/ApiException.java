package edu.java.exception.api;


import edu.java.dto.api.response.ApiErrorResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiException extends RuntimeException {
    protected final ApiErrorResponse apiErrorResponse;
}
