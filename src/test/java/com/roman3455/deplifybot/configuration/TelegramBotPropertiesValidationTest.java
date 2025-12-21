package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("DataFlowIssue")
@DisplayName("TelegramBotProperties - properties validation")
class TelegramBotPropertiesValidationTest extends DtoValidationTestSupport<TelegramBotProperties> {

    private static final int MAX_CONNECTIONS = 100;
    private static final int MAX_BYTES_SIZE = 64;
    private static final String EN_CODE = "en";
    private static final String RU_CODE = "en";

    private static TelegramBotProperties createValidProperties() {
        final int allowedHttpConnections = 40;
        final int tokenByteSize = 32;
        return new TelegramBotProperties(
                "https://example.com",
                "/telegram/webhook",
                allowedHttpConnections,
                List.of(UpdateType.MESSAGE, UpdateType.CALLBACK_QUERY),
                true,
                BotCommandScopeType.ALL_PRIVATE_CHATS,
                tokenByteSize,
                List.of(
                        new TelegramBotProperties.LanguageSpec("und", null, "DEFAULT"),
                        new TelegramBotProperties.LanguageSpec(EN_CODE, EN_CODE, EN_CODE.toUpperCase()),
                        new TelegramBotProperties.LanguageSpec(RU_CODE, RU_CODE, RU_CODE.toUpperCase())
                )
        );
    }

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                invalidWebhookUrl(),
                invalidWebhookPath(),
                invalidAllowedHttpConnections(),
                invalidSetUserCommandMenu(),
                invalidSecretTokenBytesSize(),
                invalidLanguageSpecs()
        ).flatMap(Function.identity());
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, createValidProperties())
        );
    }

    @Test
    @DisplayName("Should return valid Locale from TelegramBotProperties.LanguageSpec getLocaleTag()")
    void shouldReturnValidLocaleFromTelegramBotProperties() {
        List<TelegramBotProperties.LanguageSpec> languageSpec = createValidProperties().languageSpecs();
        Locale given = languageSpec.getFirst().getLocale();
        assertThat(given.toLanguageTag()).isEqualTo("und");
    }

    private static Stream<Arguments> invalidWebhookUrl() {
        return Stream.of(
                Arguments.of(
                        "field 'webhookUrl' is blank (@NotBlank)",
                        new TelegramBotProperties(
                                " ",
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "webhookUrl",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'webhookUrl' mismatch pattern (@Pattern)",
                        new TelegramBotProperties(
                                "htps://example.com",
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "webhookUrl",
                        "{SetWebhookRequest.url.Pattern.message}"
                )
        );
    }

    private static Stream<Arguments> invalidWebhookPath() {
        return Stream.of(
                Arguments.of(
                        "field 'webhookPath' is blank (@NotBlank)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                " ",
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "webhookPath",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'webhookPath' mismatch pattern (@Pattern)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                "telegram/webhook",
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "webhookPath",
                        "{SetWebhookRequest.path.Pattern.message}"
                )
        );
    }

    private static Stream<Arguments> invalidAllowedHttpConnections() {
        return Stream.of(
                Arguments.of(
                        "field 'allowedHttpConnections' has value below min (@Min)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                0,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "allowedHttpConnections",
                        MESSAGE_TEMPLATE_MIN_VALUE
                ),
                Arguments.of(
                        "field 'allowedHttpConnections' has value above max (@Max)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                MAX_CONNECTIONS + 1,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "allowedHttpConnections",
                        MESSAGE_TEMPLATE_MAX_VALUE
                )
        );
    }

    private static Stream<Arguments> invalidSetUserCommandMenu() {
        return Stream.of(
                Arguments.of(
                        "field 'setUserCommandMenu' is null (@NotNull)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                null,
                                createValidProperties().secretTokenBytesSize(),
                                createValidProperties().languageSpecs()
                        ),
                        "setUserCommandMenu",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    private static Stream<Arguments> invalidSecretTokenBytesSize() {
        return Stream.of(
                Arguments.of(
                        "field 'secretTokenBytesSize' has value below min (@Min)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                1,
                                createValidProperties().languageSpecs()
                        ),
                        "secretTokenBytesSize",
                        MESSAGE_TEMPLATE_MIN_VALUE
                ),
                Arguments.of(
                        "secretTokenBytesSize' has value above max (@Max)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                MAX_BYTES_SIZE + 1,
                                createValidProperties().languageSpecs()
                        ),
                        "secretTokenBytesSize",
                        MESSAGE_TEMPLATE_MAX_VALUE
                )
        );
    }

    private static Stream<Arguments> invalidLanguageSpecs() {
        return Stream.of(
                Arguments.of(
                        "field 'languageSpecs' has empty list (@NotEmpty)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                List.of()
                        ),
                        "languageSpecs",
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'languageSpecs.localeTag' is blank (@NotBlank)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                List.of(new TelegramBotProperties
                                        .LanguageSpec(" ", EN_CODE, EN_CODE.toUpperCase())
                                )
                        ),
                        "languageSpecs[0].localeTag",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'languageSpecs.languageCode' is not match ISO639-1 (@ISO6391)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                List.of(new TelegramBotProperties
                                        .LanguageSpec(EN_CODE, "xx", EN_CODE.toUpperCase())
                                )
                        ),
                        "languageSpecs[0].languageCode",
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageSpecs.countryCode' is blank (@NotBlank)",
                        new TelegramBotProperties(
                                createValidProperties().webhookUrl(),
                                createValidProperties().webhookPath(),
                                null,
                                null,
                                null,
                                createValidProperties().setUserCommandMenu(),
                                createValidProperties().secretTokenBytesSize(),
                                List.of(new TelegramBotProperties.LanguageSpec(RU_CODE, RU_CODE, " "))
                        ),
                        "languageSpecs[0].countryCode",
                        MESSAGE_TEMPLATE_NOT_BLANK
                )
        );
    }

}
