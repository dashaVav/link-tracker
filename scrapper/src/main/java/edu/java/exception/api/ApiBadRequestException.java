package edu.java.exception.api;

import edu.java.dto.api.response.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ApiBadRequestException extends ApiException {
    public ApiBadRequestException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse);
    }
}
