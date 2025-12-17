package com.roman3455.deplifybot.dto.telegram.api.ui;

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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validInlineKeyboardMarkupFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("InlineKeyboardMarkup — JSON serialization & deserialization")
class InlineKeyboardMarkupJsonTest extends DtoJsonMarshallingTestSupport<InlineKeyboardMarkup> {

    private static final String PATH = "/fixture/telegram/ui/inline_keyboard_markup/inline_keyboard_markup_";

    @Autowired
    private JacksonTester<InlineKeyboardMarkup> jsonTester;

    @Override
    protected JacksonTester<InlineKeyboardMarkup> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validInlineKeyboardMarkupFullPayload(),
                        PATH + "full.json",
                        List.of("$.inlineKeyboard")
                )
        );
    }

}
