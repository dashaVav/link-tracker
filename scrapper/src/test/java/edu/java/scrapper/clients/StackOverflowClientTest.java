package edu.java.scrapper.clients;

import com.github.tomakehurst.wiremock.WireMockServer;
import edu.java.client.StackOverflowClient;
import edu.java.client.impl.StackOverflowClientImpl;
import edu.java.dto.stackoverflow.StackOverflowDTO;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertThrows;

public class StackOverflowClientTest {
    private static WireMockServer wireMockServer;
    private static String baseUrl;

    @BeforeAll
    public static void setUp() {
        wireMockServer = new WireMockServer(3000);
        wireMockServer.start();
        baseUrl = "http://localhost:" + wireMockServer.port();
    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void testFetchRepo() {
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);
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
        StackOverflowClient stackOverflowClient = new StackOverflowClientImpl(baseUrl);

        long questionId = 1;
        wireMockServer.stubFor(get(urlEqualTo(
            "/questions/" + questionId + "?order=desc&sort=activity&site=stackoverflow"))
            .willReturn(aResponse()
                .withStatus(404)));

        assertThrows(WebClientResponseException.class, () -> stackOverflowClient.fetchAnswersByQuestionId(questionId));
    }
}
