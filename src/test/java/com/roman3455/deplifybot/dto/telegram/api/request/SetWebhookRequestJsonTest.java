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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSetWebhookRequestFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSetWebhookRequestRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("SetWebhookRequest — JSON serialization & deserialization")
class SetWebhookRequestJsonTest extends DtoJsonMarshallingTestSupport<SetWebhookRequest> {

    private static final String PATH = "/fixture/telegram/request/set_webhook_request/set_webhook_request_";

    @Autowired
    private JacksonTester<SetWebhookRequest> jsonTester;

    @Override
    protected JacksonTester<SetWebhookRequest> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validSetWebhookRequestFullPayload(),
                        PATH + "full.json",
                        List.of("$.maxConnections", "$.allowedUpdates", "$.dropPendingUpdates", "$.secretToken")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validSetWebhookRequestRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.max_connections", "$.allowed_updates", "$.drop_pendingUpdates", "$.secret_token")
                )
        );
    }

}
