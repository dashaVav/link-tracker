package edu.java.scrapper.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static edu.java.scrapper.integration.IntegrationEnvironment.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IntegrationTest {
    @Test
    public void testConnection() {
        Assertions.assertTrue(POSTGRES.isRunning());
        assertThat(POSTGRES.getUsername()).isEqualTo("postgres");
        assertThat(POSTGRES.getPassword()).isEqualTo("postgres");
        assertThat(POSTGRES.getDatabaseName()).isEqualTo("scrapper");
    }
}
