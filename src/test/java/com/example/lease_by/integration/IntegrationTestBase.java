package com.example.lease_by.integration;

import com.example.lease_by.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
@Sql("classpath:sql/test-data.sql")
public class IntegrationTestBase {
    private static final String IMAGE_VERSION = "postgres:15.4";
    private static final String PROPERTY_NAME = "spring.datasource.url";
    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>(IMAGE_VERSION);

    @BeforeAll
    static void runContainer() {
        CONTAINER.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_NAME, CONTAINER::getJdbcUrl);
    }
}