package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSetWebhookRequestFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSetWebhookRequestRequiredPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSetWebhookRequestWithEmptyUrl;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSetWebhookRequestWithInvalidUrl;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidSetWebhookRequestWithValueBelowMinConnection;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidSetWebhookRequestWithValueAboveMaxConnection;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidSetWebhookRequestWithSizeBelowMinSecretToken;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidSetWebhookRequestWithSizeAboveMaxSecretToken;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidSetWebhookRequestMismatchPatternSecretToken;

@DisplayName("SetWebhookRequest - DTO validation")
class SetWebhookRequestValidationTest extends DtoValidationTestSupport<SetWebhookRequest> {

    private static final String URL_FIELD = "url";
    private static final String MAX_CONNECTIONS_FIELD = "maxConnections";
    private static final String SECRET_TOKEN_FIELD = "secretToken";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'url' is blank (@NotBlank)",
                        invalidSetWebhookRequestWithEmptyUrl(),
                        URL_FIELD,
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'url' mismatch pattern (@Pattern)",
                        invalidSetWebhookRequestWithInvalidUrl(),
                        URL_FIELD,
                        "{SetWebhookRequest.url.Pattern.message}"
                ),
                Arguments.of(
                        "field 'maxConnections' has value below min (@Min)",
                        invalidSetWebhookRequestWithValueBelowMinConnection(),
                        MAX_CONNECTIONS_FIELD,
                        MESSAGE_TEMPLATE_MIN_SIZE
                ),
                Arguments.of(
                        "field 'maxConnections' has value above max (@Max)",
                        invalidSetWebhookRequestWithValueAboveMaxConnection(),
                        MAX_CONNECTIONS_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'secretToken' has value below min (@Size)",
                        invalidSetWebhookRequestWithSizeBelowMinSecretToken(),
                        SECRET_TOKEN_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                ),
                Arguments.of(
                        "field 'secretToken' has value above max (@Size)",
                        invalidSetWebhookRequestWithSizeAboveMaxSecretToken(),
                        SECRET_TOKEN_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                ),
                Arguments.of(
                        "field 'secretToken' mismatch pattern (@Pattern)",
                        invalidSetWebhookRequestMismatchPatternSecretToken(),
                        SECRET_TOKEN_FIELD,
                        "{SetWebhookRequest.secretToken.Pattern.message}"
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validSetWebhookRequestFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validSetWebhookRequestRequiredPayload())
        );
    }

}
