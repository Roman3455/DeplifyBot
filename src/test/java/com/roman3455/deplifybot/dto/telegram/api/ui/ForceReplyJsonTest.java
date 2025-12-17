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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validForceReplyFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validForceReplyRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ForceReply — JSON serialization & deserialization")
class ForceReplyJsonTest extends DtoJsonMarshallingTestSupport<ForceReply> {

    private static final String PATH = "/fixture/telegram/ui/force_reply/force_reply_";

    @Autowired
    private JacksonTester<ForceReply> jsonTester;

    @Override
    protected JacksonTester<ForceReply> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validForceReplyFullPayload(),
                        PATH + "full.json",
                        List.of("$.forceReply", "$.inputFieldPlaceholder")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validForceReplyRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.input_field_placeholder")
                )
        );
    }

}
