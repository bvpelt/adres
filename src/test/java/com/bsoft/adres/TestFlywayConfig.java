package com.bsoft.adres;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class TestFlywayConfig {

    @Value("${activeProfile}")
    private String profile;

    public TestFlywayConfig() {
        log.info("Flyway configuration active profile: {}", profile);
    }

    @Bean
    @DependsOn("entityManagerFactory")
    public Flyway flyway(DataSource dataSource) {
        log.info("Flyway configuration active profile: {}", profile);

        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration/postgresql") // Adjust location as needed
                .load();
    }
}