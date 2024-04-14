package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.StackOverflowClientImpl;
import edu.java.configuration.retry.RetryProperties;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import edu.java.exception.custom.ResourceUnavailableException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertThrows;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StackOverflowClientTest {
    private static WireMockServer wireMockServer;
    private static String baseUrl;
    private static RetryProperties retryProperties;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(3001);
        wireMockServer.start();
        baseUrl = "http://localhost:" + wireMockServer.port();

        RetryBackoffSpec retry = Retry.backoff(5, Duration.ofMillis(10))
            .filter(throwable -> throwable instanceof ResourceUnavailableException ||
                throwable instanceof WebClientRequestException)
            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                throw new ResourceUnavailableException("Try later", HttpStatus.SERVICE_UNAVAILABLE);
            });
        retryProperties = new RetryProperties(List.of(HttpStatus.FORBIDDEN), retry);
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testFetchRepo() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl, retryProperties);
        long questionId = 12345;
        OffsetDateTime lastActivityDate = OffsetDateTime.now(ZoneOffset.ofHours(3));
        long answerCount = 5;
        String responseBody = String.format(
            "{\"items\": [{\"owner\": {\"account_id\": 54321, \"display_name\": \"Test User\"}, \"creation_date\": \"%s\", \"question_id\": %d, \"answer_count\": %d}]}",
            lastActivityDate,
            questionId,
            answerCount
        );

        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "/answers?order=desc&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(responseBody)));

        StackOverflowDTO stackOverflowDTO = stackOverflowClient.fetchAnswersByQuestionId(questionId);

        Assertions.assertEquals(1, stackOverflowDTO.items().size());
        StackOverflowDTO.Item item = stackOverflowDTO.items().getFirst();
        Assertions.assertEquals(Optional.of(questionId).get(), item.questionId());
        Assertions.assertEquals(lastActivityDate, item.creationDate().withOffsetSameInstant(ZoneOffset.ofHours(3)));
        Assertions.assertEquals("Test User", item.owner().displayName());
    }

    @Test
    public void testFetchRepo_NotFound() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl, retryProperties);

        long questionId = 1;
        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> stackOverflowClient.fetchAnswersByQuestionId(questionId));
    }
}
