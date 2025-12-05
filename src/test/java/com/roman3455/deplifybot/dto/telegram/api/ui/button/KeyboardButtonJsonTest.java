package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validKeyboardButtonFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validKeyboardButtonRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("KeyboardButton — JSON serialization & deserialization")
class KeyboardButtonJsonTest extends DtoJsonMarshallingTestSupport<KeyboardButton> {

    private static final String PATH = "/fixture/telegram/ui/button/keyboard_button/keyboard_button_";

    @Autowired
    private JacksonTester<KeyboardButton> jsonTester;

    @Override
    protected JacksonTester<KeyboardButton> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validKeyboardButtonFullPayload(),
                        PATH + "full.json",
                        List.of("$.requestChat")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validKeyboardButtonRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.request_chat")
                )
        );
    }

}
