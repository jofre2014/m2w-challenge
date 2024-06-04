package com.challenge.w2m.integrationTest;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractIntegrationTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(
            "postgres:16.3")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

}
