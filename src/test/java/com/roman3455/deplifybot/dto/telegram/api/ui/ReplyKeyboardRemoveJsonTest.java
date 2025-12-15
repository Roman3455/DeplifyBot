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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validReplyKeyboardRemoveFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ReplyKeyboardRemove — JSON serialization & deserialization")
class ReplyKeyboardRemoveJsonTest extends DtoJsonMarshallingTestSupport<ReplyKeyboardRemove> {

    private static final String PATH = "/fixture/telegram/ui/reply_keyboard_remove/reply_keyboard_remove_";

    @Autowired
    private JacksonTester<ReplyKeyboardRemove> jsonTester;

    @Override
    protected JacksonTester<ReplyKeyboardRemove> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validReplyKeyboardRemoveFullPayload(),
                        PATH + "full.json",
                        List.of("$.removeKeyboard")
                )
        );
    }

}
