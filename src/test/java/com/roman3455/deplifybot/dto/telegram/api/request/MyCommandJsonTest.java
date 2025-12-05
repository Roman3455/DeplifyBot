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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validMyCommandFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("MyCommand — JSON serialization & deserialization")
class MyCommandJsonTest extends DtoJsonMarshallingTestSupport<MyCommand> {

    private static final String PATH = "/fixture/telegram/request/my_command/my_command_";

    @Autowired
    private JacksonTester<MyCommand> jsonTester;

    @Override
    protected JacksonTester<MyCommand> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validMyCommandFullPayload(),
                        PATH + "full.json",
                        List.of()
                )
        );
    }

}
