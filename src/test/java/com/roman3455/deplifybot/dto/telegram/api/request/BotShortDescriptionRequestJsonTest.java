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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validBotShortDescriptionRequestFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .validBotShortDescriptionRequestDescriptionPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .validBotShortDescriptionRequestLanguageCodePayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("BotShortDescriptionRequest — JSON serialization & deserialization")
class BotShortDescriptionRequestJsonTest extends DtoJsonMarshallingTestSupport<BotShortDescriptionRequest> {

    private static final String PATH =
            "/fixture/telegram/request/bot_short_description_request/bot_short_description_request_";

    @Autowired
    private JacksonTester<BotShortDescriptionRequest> jsonTester;

    @Override
    protected JacksonTester<BotShortDescriptionRequest> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validBotShortDescriptionRequestFullPayload(),
                        PATH + "full.json",
                        List.of("$.shortDescription", "$.languageCode")
                ),
                Arguments.of(
                        "description payload",
                        validBotShortDescriptionRequestDescriptionPayload(),
                        PATH + "short_description.json",
                        List.of("$.language_code")
                ),
                Arguments.of(
                        "languageCode payload",
                        validBotShortDescriptionRequestLanguageCodePayload(),
                        PATH + "language_code.json",
                        List.of("$.short_description")
                )
        );
    }

}
