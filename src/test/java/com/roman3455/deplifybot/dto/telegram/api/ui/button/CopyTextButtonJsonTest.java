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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validCopyTextButtonFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("CopyTextButton — JSON serialization & deserialization")
class CopyTextButtonJsonTest extends DtoJsonMarshallingTestSupport<CopyTextButton> {

    private static final String PATH = "/fixture/telegram/ui/button/copy_text_button/copy_text_button_";

    @Autowired
    private JacksonTester<CopyTextButton> jsonTester;

    @Override
    protected JacksonTester<CopyTextButton> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validCopyTextButtonFullPayload(),
                        PATH + "full.json",
                        List.of()
                )
        );
    }

}
