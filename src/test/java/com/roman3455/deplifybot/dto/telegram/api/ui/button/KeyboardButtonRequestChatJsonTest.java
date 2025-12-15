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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validKeyboardButtonRequestChatFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validKeyboardButtonRequestChatRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("KeyboardButtonRequest — JSON serialization & deserialization")
class KeyboardButtonRequestChatJsonTest extends DtoJsonMarshallingTestSupport<KeyboardButtonRequestChat> {

    private static final String PATH = "/fixture/telegram/ui/button/keyboard_button_request_chat/";

    @Autowired
    private JacksonTester<KeyboardButtonRequestChat> jsonTester;

    @Override
    protected JacksonTester<KeyboardButtonRequestChat> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validKeyboardButtonRequestChatFullPayload(),
                        PATH + "keyboard_button_request_chat_full.json",
                        List.of(
                                "$.requestId",
                                "$.chatIsChannel",
                                "$.userAdministratorRights",
                                "$.botAdministratorRights",
                                "$.botIsMember",
                                "$.requestTitle"
                        )
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validKeyboardButtonRequestChatRequiredPayload(),
                        PATH + "keyboard_button_request_chat_required.json",
                        List.of(
                                "$.userAdministratorRights",
                                "$.botAdministratorRights",
                                "$.botIsMember",
                                "$.requestTitle"
                        )
                )
        );
    }

}
