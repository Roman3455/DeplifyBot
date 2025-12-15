package com.roman3455.deplifybot.test_utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;


/**
 * Base contract for JSON marshalling tests.
 *
 * <p>This support class verifies the <strong>happy-path</strong> JSON contract for a given payload type:
 * <ul>
 *   <li>Object → JSON matches the expected fixture.</li>
 *   <li>JSON fixture → Object matches the expected payload.</li>
 *   <li>JSON fixture → Object → JSON fixture round-trip is stable.</li>
 *   <li>Object → JSON → Object round-trip is stable.</li>
 * </ul>
 * </p>
 *
 * @param <T> payload type under test.
 */
@SuppressWarnings("unused")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DtoJsonMarshallingTestSupport<T> {

    protected static final String CASE_NAME_FULL_PAYLOAD = "full payload";
    protected static final String CASE_NAME_REQUIRED_PAYLOAD = "required payload";

    /**
     * Provides a configured {@link JacksonTester} for the payload type {@code T}.
     *
     * @return configured {@link JacksonTester} for the tested type.
     */
    protected abstract JacksonTester<T> tester();

    /**
     * Provides test cases for JSON marshalling.
     *
     * <p>Each {@link Arguments} instance <strong>must</strong> follow the contract:
     * <pre>
     * Arguments.of(
     *     String caseName,
     *     T payload,
     *     String fixturePath,
     *     List&lt;String&gt; absentJsonPaths
     * )
     * </pre>
     * </p>
     *
     * @return stream of marshalling test cases.
     */
    protected abstract Stream<Arguments> provideArguments();

    /**
     * Verifies that the given payload is serialized into JSON that matches the expected fixture.
     *
     * <p>Additionally, {@code absentJsonPaths} can be used to assert that specific JSON paths
     * are not present in the serialized JSON(for example, internal or sensitive fields).</p>
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideArguments")
    @DisplayName("Should serialize object into expected JSON fixture:")
    void shouldSerializeObjectAndMatchFixture(
            final String caseName,
            final T payload,
            final String fixturePath,
            final List<String> absentJsonPaths
    ) throws IOException {
        JacksonTester<T> jsonTester = tester();
        ClassPathResource resource = new ClassPathResource(fixturePath);
        JsonContent<T> serialized = jsonTester.write(payload);
        then(serialized).isNotNull()
                .isEqualToJson(resource);
        if (absentJsonPaths != null && !absentJsonPaths.isEmpty()) {
            absentJsonPaths.forEach(path -> then(serialized).doesNotHaveJsonPath(path));
        }
    }

    /**
     * Verifies that the JSON fixture can be deserialized into an object
     * equal to the expected payload instance.
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideArguments")
    @DisplayName("Should deserialize JSON fixture into expected object:")
    void shouldDeserializeFixtureAndMatchObject(
            final String caseName,
            final T payload,
            final String fixturePath,
            final List<String> absentJsonPaths
    ) throws IOException {
        JacksonTester<T> jsonTester = tester();
        ClassPathResource resource = new ClassPathResource(fixturePath);
        T deserialized = jsonTester.readObject(resource);
        then(deserialized).isNotNull()
                .isEqualTo(payload);
    }

    /**
     * Verifies that JSON fixture → Object → JSON fixture round-trip
     * is stable and produces JSON identical to the original fixture.
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideArguments")
    @DisplayName("Should round-trip JSON fixture -> Object -> JSON fixture:")
    void shouldRoundTripJsonFixture(
            final String caseName,
            final T payload,
            final String fixturePath,
            final List<String> absentJsonPaths
    ) throws Exception {
        JacksonTester<T> jsonTester = tester();
        ClassPathResource resource = new ClassPathResource(fixturePath);
        T deserialized = jsonTester.readObject(resource);
        JsonContent<T> serialized = jsonTester.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(resource);
    }

    /**
     * Verifies that Object → JSON → Object round-trip
     * is stable and produces an object equal to the original payload.
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideArguments")
    @DisplayName("Should round-trip Object -> JSON fixture -> Object:")
    void shouldRoundTripObject(
            final String caseName,
            final T payload,
            final String fixturePath,
            final List<String> absentJsonPaths
    ) throws Exception {
        JacksonTester<T> jsonTester = tester();
        JsonContent<T> serialized = jsonTester.write(payload);
        T deserialized = jsonTester.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(payload);
    }

}
