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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSetMyCommandsRequestFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSetMyCommandsRequestRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("SetMyCommandsRequest — JSON serialization & deserialization")
class SetMyCommandsRequestJsonTest extends DtoJsonMarshallingTestSupport<SetMyCommandsRequest> {

    private static final String PATH = "/fixture/telegram/request/set_my_commands_request/set_my_commands_request_";

    @Autowired
    private JacksonTester<SetMyCommandsRequest> jsonTester;

    @Override
    protected JacksonTester<SetMyCommandsRequest> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validSetMyCommandsRequestFullPayload(),
                        PATH + "full.json",
                        List.of("$.languageCode")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validSetMyCommandsRequestRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.scope", "$.language_code")
                )
        );
    }

}
