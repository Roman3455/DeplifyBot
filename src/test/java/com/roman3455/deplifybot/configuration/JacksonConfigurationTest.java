package com.roman3455.deplifybot.configuration;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("JacksonConfiguration — object mapper behavior")
class JacksonConfigurationTest {

    private record SampleObject(String firstName, LocalDate createdAt, String nullable, TestEnum status) {
        static final int YEAR = 2025;
        static SampleObject createSampleObject() {
            return new SampleObject("John", LocalDate.of(YEAR, 1, 1), null, TestEnum.VALUE);
        }
    }

    private enum TestEnum {
        VALUE, @JsonEnumDefaultValue UNKNOWN
    }

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
    @DisplayName("Serializes property names using snake_case")
    void serializesPropertyNamesUsingSnakeCase() throws Exception {
        var actual = objectMapper.writeValueAsString(sampleObject);
        assertThat(actual).contains("first_name");
    }

    @Test
    @DisplayName("Excludes null fields from serialized JSON")
    void excludesNullFieldsFromSerializedJson() throws Exception {
        var actual = objectMapper.writeValueAsString(sampleObject);
        assertThat(actual).doesNotContain("nullable");
    }

    @Test
    @DisplayName("Serializes Java time fields in ISO-8601 format")
    void serializesJavaTimeFieldsInIsoFormat() throws Exception {
        var actual = objectMapper.writeValueAsString(sampleObject);
        assertThat(actual).contains("2025-01-01");
    }

    @Test
    @DisplayName("Deserializes unknown enum values as default")
    void deserializesUnknownEnumValuesAsDefault() throws Exception {
        var given = """
                {"first_name":"John","created_at":"2025-01-01","status":"INVALID"}
                """;
        var actual = objectMapper.readValue(given, SampleObject.class);
        assertThat(actual.status()).isEqualTo(TestEnum.UNKNOWN);
    }

}
