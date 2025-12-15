package com.roman3455.deplifybot.dto.telegram.api.request;

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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validBotDescriptionRequestFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validBotDescriptionRequestDescriptionPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validBotDescriptionRequestLanguageCodePayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("BotDescriptionRequest — JSON serialization & deserialization")
class BotDescriptionRequestJsonTest extends DtoJsonMarshallingTestSupport<BotDescriptionRequest> {

    private static final String PATH = "/fixture/telegram/request/bot_description_request/bot_description_request_";

    @Autowired
    private JacksonTester<BotDescriptionRequest> jsonTester;

    @Override
    protected JacksonTester<BotDescriptionRequest> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validBotDescriptionRequestFullPayload(),
                        PATH + "full.json",
                        List.of("$.languageCode")
                ),
                Arguments.of(
                        "description payload",
                        validBotDescriptionRequestDescriptionPayload(),
                        PATH + "description.json",
                        List.of("$.language_code")
                ),
                Arguments.of(
                        "languageCode payload",
                        validBotDescriptionRequestLanguageCodePayload(),
                        PATH + "language_code.json",
                        List.of("$.description")
                )
        );
    }

}
