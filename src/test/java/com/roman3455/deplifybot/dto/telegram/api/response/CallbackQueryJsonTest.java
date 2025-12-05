package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validCallbackQueryFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validCallbackQueryRequiredPayload;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("CallbackQuery — JSON serialization & deserialization")
class CallbackQueryJsonTest extends DtoJsonMarshallingTestSupport<CallbackQuery> {

    private static final String PATH = "/fixture/telegram/response/callback_query/callback_query_";

    @Autowired
    private JacksonTester<CallbackQuery> jsonTester;

    @Override
    protected JacksonTester<CallbackQuery> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validCallbackQueryFullPayload(),
                        PATH + "full.json",
                        List.of()
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validCallbackQueryRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.message", "$.data")
                )
        );
    }

    @Test
    @DisplayName("Should return true when payload has field 'message'")
    void shouldReturnTrueWhenPayloadHasFieldMessage() {
        assertTrue(validCallbackQueryFullPayload().hasMessage());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'message'")
    void shouldReturnFalseWhenPayloadHasNotFieldMessage() {
        assertFalse(validCallbackQueryRequiredPayload().hasMessage());
    }

    @Test
    @DisplayName("Should return true when payload has field 'data'")
    void shouldReturnTrueWhenPayloadHasFieldData() {
        assertTrue(validCallbackQueryFullPayload().hasData());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'data'")
    void shouldReturnFalseWhenPayloadHasNotFieldData() {
        assertFalse(validCallbackQueryRequiredPayload().hasData());
    }

}
