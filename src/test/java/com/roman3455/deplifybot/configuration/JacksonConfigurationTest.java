package com.roman3455.deplifybot.configuration;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("JacksonConfiguration — object mapper behavior")
class JacksonConfigurationTest {

    private final String PATH = "/fixture/configuration/jackson_configuration/";

    private final JacksonConfiguration configuration = new JacksonConfiguration();

    private enum TestEnum {
        OK, @JsonEnumDefaultValue UNKNOWN
    }

    private record JsonTestObject(String firstName, String lastName, Integer age, Instant createdAt, TestEnum status) {
        static final Integer AGE = 20;
        static final Instant DATE_TIME = Instant.ofEpochSecond(1710248593);

        static JsonTestObject createTestObject() {
            return new JsonTestObject("John", null, AGE, DATE_TIME, TestEnum.OK);
        }
    }

    private ObjectMapper objectMapper;
    private JsonTestObject testObject;

    @BeforeAll
    void setUp() {
        testObject = JsonTestObject.createTestObject();
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        configuration.jackson2ObjectMapperBuilderCustomizer().customize(builder);
        objectMapper = builder.build();
    }

    @Test
    @DisplayName("Should configure ObjectMapper with required features and strategies")
    void shouldConfigureObjectMapperWithRequiredFeaturesAndStrategies() {
        assertThat(objectMapper).isNotNull();
        assertTrue(objectMapper.getRegisteredModuleIds().contains("jackson-datatype-jsr310"));
        assertThat(objectMapper.getPropertyNamingStrategy()).isEqualTo(PropertyNamingStrategies.SNAKE_CASE);
        assertThat(objectMapper.getSerializationConfig().getDefaultPropertyInclusion().getValueInclusion())
                .isEqualTo(JsonInclude.Include.NON_NULL);
        assertTrue(objectMapper.isEnabled(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE));
        assertFalse(objectMapper.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS));
        assertFalse(objectMapper.isEnabled(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
    }

    @Test
    @DisplayName("Should serialize snake_case, exclude nulls and write Instant as ISO-8601 format")
    void shouldSerializeSnakeCaseAndExcludeNullFields() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(testObject);
        assertThat(json).isNotBlank();
        assertThat(json).contains("\"first_name\":\"John\"");
        assertThat(json).doesNotContain("last_name");
        assertThat(json).contains("\"age\":20");
        assertThat(json).contains("\"created_at\":\"2024-03-12T13:03:13Z\"");
        assertThat(json).contains("\"status\":\"OK\"");
    }

    @Test
    @DisplayName("Should deserialize snake_case JSON into Object")
    void shouldDeserializeSnakeCaseJSON() throws IOException {
        var pathResource = new ClassPathResource(PATH + "valid_object.json");
        var deserialized = objectMapper.readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class);
        assertThat(deserialized).isNotNull();
        assertThat(deserialized.firstName()).isEqualTo(testObject.firstName());
        assertNull(deserialized.lastName());
        assertThat(deserialized.age()).isEqualTo(testObject.age());
        assertThat(deserialized.createdAt()).isEqualTo(testObject.createdAt());
        assertThat(deserialized.status()).isEqualTo(testObject.status());
    }

    @Test
    @DisplayName("Should ignore unknown properties during deserialization and not fail")
    void shouldIgnoreUnknownPropertiesDuringDeserializationAndNotFail() throws IOException {
        var pathResource = new ClassPathResource(PATH + "with_unknown_field.json");
        var deserialized = objectMapper.readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class);
        assertThat(deserialized).isNotNull();
        assertThat(deserialized).isEqualTo(testObject);
    }

    @Test
    @DisplayName("Should deserialize unknown enum values to @JsonEnumDefaultValue")
    void shouldDeserializeUnknownEnumValuesToJsonEnumDefaultValue() throws IOException {
        var pathResource = new ClassPathResource(PATH + "unknown_enum.json");
        var deserialized = objectMapper.readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class);
        assertThat(deserialized.status()).isEqualTo(TestEnum.UNKNOWN);
    }

    @Test
    @DisplayName("Should fail on malformed JSON syntax")
    void shouldFailOnMalformedJSONSyntax() {
        var pathResource = new ClassPathResource(PATH + "incorrect_syntax.json");
        assertThatThrownBy(() -> objectMapper
                .readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class))
                .isInstanceOf(JsonParseException.class);
    }

    @Test
    @DisplayName("Should fail on truncated JSON")
    void shouldFailOnTruncatedJSON() {
        var pathResource = new ClassPathResource(PATH + "truncated.json");
        assertThatThrownBy(() -> objectMapper
                .readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class))
                .isInstanceOf(JsonParseException.class);
    }

    @Test
    @DisplayName("Should fail when JSON type mismatches target type")
    void shouldFailWhenJSONTypeMismatch() {
        var pathResource = new ClassPathResource(PATH + "mismatch_type.json");
        assertThatThrownBy(() -> objectMapper
                .readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class))
                .isInstanceOf(JsonMappingException.class);
    }

    @Test
    @DisplayName("Should fail when Instant has invalid format")
    void shouldFailWhenInstantHasInvalidFormat() {
        var pathResource = new ClassPathResource(PATH + "invalid_time_format.json");
        assertThatThrownBy(() -> objectMapper
                .readValue(pathResource.getInputStream().readAllBytes(), JsonTestObject.class))
                .isInstanceOf(JsonMappingException.class);
    }

}
