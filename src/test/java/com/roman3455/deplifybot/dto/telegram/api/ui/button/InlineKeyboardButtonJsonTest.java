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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonCallbackDataPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonCopyTextPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonUrlPayload;


@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("InlineKeyboardButton — JSON serialization & deserialization")
class InlineKeyboardButtonJsonTest extends DtoJsonMarshallingTestSupport<InlineKeyboardButton> {

    private static final String PATH = "/fixture/telegram/ui/button/inline_keyboard_button/inline_keyboard_button_";

    @Autowired
    private JacksonTester<InlineKeyboardButton> jsonTester;

    @Override
    protected JacksonTester<InlineKeyboardButton> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "callback data payload",
                        validInlineKeyboardButtonCallbackDataPayload(),
                        PATH + "callback_data.json",
                        List.of("$.url", "$.callbackData", "$.copy_text")
                ),
                Arguments.of(
                        "copy text payload",
                        validInlineKeyboardButtonCopyTextPayload(),
                        PATH + "copy_text.json",
                        List.of("$.url", "$.callback_data", "$.copyText")
                ),
                Arguments.of(
                        "url payload",
                        validInlineKeyboardButtonUrlPayload(),
                        PATH + "url.json",
                        List.of("$.callback_data", "$.copy_text")
                )
        );
    }

}
