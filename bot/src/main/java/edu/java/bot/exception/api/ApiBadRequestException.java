package edu.java.bot.exception.api;

import edu.java.bot.dto.api.ApiErrorResponse;
import lombok.Getter;

@Getter
public class ApiBadRequestException extends ApiException {
    public ApiBadRequestException(ApiErrorResponse apiErrorResponse) {
        super(apiErrorResponse);
    }
}
