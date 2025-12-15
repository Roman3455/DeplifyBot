package com.roman3455.deplifybot.dto.telegram.api.response;

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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUserFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUserRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("User — JSON serialization & deserialization")
class UserJsonTest extends DtoJsonMarshallingTestSupport<User> {

    private static final String PATH = "/fixture/telegram/response/user/user_";

    @Autowired
    private JacksonTester<User> jsonTester;

    @Override
    protected JacksonTester<User> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validUserFullPayload(),
                        PATH + "full.json",
                        List.of("$.isBot", "$.firstName", "$.languageCode")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validUserRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.username", "$.language_code")
                )
        );
    }

}
