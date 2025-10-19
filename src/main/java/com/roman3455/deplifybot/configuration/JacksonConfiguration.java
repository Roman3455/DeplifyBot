package com.roman3455.deplifybot.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures Jackson's ObjectMapper for the application.
 *
 * <p>Settings applied:</p>
 * <ul>
 *     <li>Support for Java 8 Date/Time types via {@link JavaTimeModule}.</li>
 *     <li>Snake case property naming ({@link PropertyNamingStrategies#SNAKE_CASE}).</li>
 *     <li>Exclude null fields from serialization ({@link JsonInclude.Include#NON_NULL}).</li>
 *     <li>Deserialize unknown enum values using default.</li>
 *     <li>Disable timestamps for dates and ignoring unknown properties.</li>
 * </ul>
 */
@Configuration
public class JacksonConfiguration {

    /**
     * Customizes Jackson's {@code ObjectMapper} used by Spring Boot for JSON serialization and deserialization.
     *
     * @return a {@link Jackson2ObjectMapperBuilderCustomizer} that applies modules, naming strategies, inclusion
     * rules, and feature toggles to the ObjectMapper.
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder
                .modulesToInstall(new JavaTimeModule())
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                );
    }

}
