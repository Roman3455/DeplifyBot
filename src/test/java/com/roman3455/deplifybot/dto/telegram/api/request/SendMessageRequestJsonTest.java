package com.roman3455.deplifybot.dto.telegram.api.request;

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

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSendMessageRequestFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSendMessageRequestRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .validSendMessageRequestPayloadWithoutReplyMarkup;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("SendMessageRequest — JSON serialization & deserialization")
class SendMessageRequestJsonTest extends DtoJsonMarshallingTestSupport<SendMessageRequest> {

    private static final String PATH = "/fixture/telegram/request/send_message_request/send_message_request_";

    @Autowired
    private JacksonTester<SendMessageRequest> jsonTester;

    @Override
    protected JacksonTester<SendMessageRequest> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "without reply markup payload",
                        validSendMessageRequestPayloadWithoutReplyMarkup(),
                        PATH + "without_reply_markup.json",
                        List.of(
                                "$.chatId",
                                "$.messageThreadId",
                                "$.parseMode",
                                "$.disableNotification",
                                "$.protectContent"
                        )
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validSendMessageRequestRequiredPayload(),
                        PATH + "required.json",
                        List.of(
                                "$.message_thread_id",
                                "$.parse_mode",
                                "$.disable_notification",
                                "$.protect_content",
                                "$.reply_markup"
                        )
                )
        );
    }

    @Test
    @DisplayName("Should serialize full payload then deserialize and does not have 'reply_markup' field")
    void shouldSerializeFullPayloadAndDeserializeWithoutReplyMarkup() throws IOException {
        var serialized = jsonTester.write(validSendMessageRequestFullPayload());
        then(serialized).isNotNull()
                .isEqualToJson(PATH + "full.json")
                .hasJsonPath("$.reply_markup");
        var deserialized = jsonTester.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .hasFieldOrPropertyWithValue("replyMarkup", null);
    }

}
