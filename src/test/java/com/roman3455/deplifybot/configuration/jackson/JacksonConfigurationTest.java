package com.roman3455.deplifybot.configuration.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JacksonConfigurationTest {

    private ObjectMapper objectMapper;
    private SampleObject sampleObject;

    @BeforeEach
    void setUp() {
        var builder = new Jackson2ObjectMapperBuilder()
                .modulesToInstall(new JavaTimeModule())
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .featuresToEnable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                .featuresToDisable(
                        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                );
        objectMapper = builder.build();

        sampleObject = SampleObject.createSampleObject();
    }

    @Test
    void shouldUseSnakeCaseNaming() throws Exception {
        var json = objectMapper.writeValueAsString(sampleObject);
        assertThat(json).contains("first_name");
    }

    @Test
    void shouldExcludeNullValues() throws Exception {
        var json = objectMapper.writeValueAsString(sampleObject);
        assertThat(json).doesNotContain("nullable");
    }

    @Test
    void shouldSerializeJavaTimeProperly() throws Exception {
        var json = objectMapper.writeValueAsString(sampleObject);
        assertThat(json).contains("2025-01-01");
    }

    @Test
    void shouldReadUnknownEnumAsDefaultValue() throws Exception {
        var json = """
                {"first_name":"John","created_at":"2025-01-01","status":"INVALID"}
                """;
        var result = objectMapper.readValue(json, SampleObject.class);
        assertThat(result.status()).isEqualTo(TestEnum.UNKNOWN);
    }

}
