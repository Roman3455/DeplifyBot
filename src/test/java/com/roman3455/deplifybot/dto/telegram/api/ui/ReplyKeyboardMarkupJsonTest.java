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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validReplyKeyboardMarkupFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validReplyKeyboardMarkupRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ReplyKeyboardMarkup — JSON serialization & deserialization")
class ReplyKeyboardMarkupJsonTest extends DtoJsonMarshallingTestSupport<ReplyKeyboardMarkup> {

    private static final String PATH = "/fixture/telegram/ui/reply_keyboard_markup/reply_keyboard_markup_";

    @Autowired
    private JacksonTester<ReplyKeyboardMarkup> jsonTester;

    @Override
    protected JacksonTester<ReplyKeyboardMarkup> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validReplyKeyboardMarkupFullPayload(),
                        PATH + "full.json",
                        List.of("$.isPersistent", "$.resizeKeyboard", "$.oneTimeKeyboard", "$.inputFieldPlaceholder")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validReplyKeyboardMarkupRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.resize_keyboard", "$.one_time_keyboard", "$.input_field_placeholder")
                )
        );
    }
}
