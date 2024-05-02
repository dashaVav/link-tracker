package edu.java.bot.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CounterConfig {
    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${management.metrics.processed-updates.name}")
    private String metricName;

    @Bean
    public Counter updatesCounter(MeterRegistry registry) {
        return Counter.builder(metricName)
            .tag("application", applicationName)
            .register(registry);
    }

}
