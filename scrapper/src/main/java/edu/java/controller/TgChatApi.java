package edu.java.controller;

import edu.java.dto.api.request.AddChatRequest;
import edu.java.dto.api.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface TgChatApi {

    /**
     * DELETE /tg-chat/{id} : Удалить чат
     *
     * @param tgChatId (required)
     * @return Чат успешно удалён (status code 200)
     *     or Некорректные параметры запроса (status code 400)
     *     or Чат не существует (status code 404)
     */
    @Operation(
        operationId = "tgChatIdDelete",
        summary = "Удалить чат",
        responses = {
            @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            }),
            @ApiResponse(responseCode = "404", description = "Чат не существует", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/tg-chat/{id}",
        produces = {"application/json"}
    )
    ResponseEntity<Void> removeChat(
        @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long tgChatId
    );

    /**
     * POST /tg-chat/{id} : Зарегистрировать чат
     *
     * @param tgChatId       (required)
     * @param addChatRequest (required)
     * @return Чат зарегистрирован (status code 200)
     *     or Некорректные параметры запроса (status code 400)
     */
    @Operation(
        operationId = "tgChatIdPost",
        summary = "Зарегистрировать чат",
        responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/tg-chat/{id}",
        produces = {"application/json"}
    )
    ResponseEntity<Void> addChat(
        @Parameter(name = "id", required = true, in = ParameterIn.PATH) @PathVariable("id") Long tgChatId,
        @Parameter(name = "AddChatRequest", required = true) @Valid @RequestBody
        AddChatRequest addChatRequest
    );

}
