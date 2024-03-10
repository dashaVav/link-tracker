package edu.java.scrapper.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static edu.java.scrapper.integration.IntegrationEnvironment.POSTGRES;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class IntegrationTest {
    @Test
    public void testConnection() {
        Assertions.assertTrue(POSTGRES.isRunning());
        assertThat(POSTGRES.getUsername()).isEqualTo("postgres");
        assertThat(POSTGRES.getPassword()).isEqualTo("postgres");
        assertThat(POSTGRES.getDatabaseName()).isEqualTo("scrapper");
    }
}
