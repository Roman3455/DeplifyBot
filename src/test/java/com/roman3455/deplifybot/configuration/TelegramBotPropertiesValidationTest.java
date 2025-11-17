package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("TelegramBotProperties - bean validation")
public class TelegramBotPropertiesValidationTest extends ValidationTestSupport {

    private static final int MAX_CONNECTIONS = 40;
    private static final int BYTES_SIZE = 32;
    private static List<String> allowedUpdateTypes;
    private static TelegramBotProperties.Connections connections;
    private static TelegramBotProperties.Webhook webhook;
    private static TelegramBotProperties.Token token;

    @BeforeAll
    public static void setup() {
        allowedUpdateTypes = List.of("message", "callback_query");
        connections = new TelegramBotProperties.Connections(MAX_CONNECTIONS);
        webhook = new TelegramBotProperties.Webhook("https://example.com", "/telegram/webhook");
        token = new TelegramBotProperties.Token(BYTES_SIZE);
    }

    @Test
    @DisplayName("Should pass validation for valid payload")
    void shouldPassValidationPayload() {
        var valid = new TelegramBotProperties(allowedUpdateTypes, connections, webhook, token);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'allowedUpdateTypes' is empty (@NotEmpty)")
    void shouldFailValidationAllowedUpdateTypesEmptyConstraint() {
        final String field = "allowedUpdateTypes";
        final String messageTemplate = "{jakarta.validation.constraints.NotEmpty.message}";
        List<String> emptyAllowedUpdateTypes = List.of();
        var invalid = new TelegramBotProperties(emptyAllowedUpdateTypes, connections, webhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when element of 'allowedUpdateTypes' is blank (@NotBlank)")
    void shouldFailValidationAllowedUpdateTypesElementBlank() {
        final String field = "allowedUpdateTypes[0].<list element>";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        List<String> invalidAllowedUpdateTypes = List.of("  ");
        var invalid = new TelegramBotProperties(invalidAllowedUpdateTypes, connections, webhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'connections' is null (@NotNull)")
    void shouldFailValidationConnectionsNullConstraint() {
        final String field = "connections";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new TelegramBotProperties(allowedUpdateTypes, null, webhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'webhook' is null (@NotNull)")
    void shouldFailValidationWebhookNullConstraint() {
        final String field = "webhook";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new TelegramBotProperties(allowedUpdateTypes, connections, null, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'token' is null (@NotNull)")
    void shouldFailValidationTokenNullConstraint() {
        final String field = "token";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new TelegramBotProperties(allowedUpdateTypes, connections, webhook, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when 'connections.value' is out of range (@Min/@Max)")
    void shouldFailValidationConnectionsValueRange() {
        final String field = "connections.value";
        final String messageTemplate = "{jakarta.validation.constraints.Min.message}";
        var invalidConnections = new TelegramBotProperties.Connections(0);
        var invalid = new TelegramBotProperties(allowedUpdateTypes, invalidConnections, webhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when 'webhook.url' is not HTTPS (@Pattern)")
    void shouldFailValidationWebhookUrlPattern() {
        final String field = "webhook.url";
        final String messageTemplate = "{SetWebhookRequest.url.Pattern.message}";
        var invalidWebhook = new TelegramBotProperties.Webhook("http://insecure.example.com", "/telegram/webhook");
        var invalid = new TelegramBotProperties(allowedUpdateTypes, connections, invalidWebhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when 'webhook.path' is blank (@NotBlank)")
    void shouldFailValidationWebhookPathBlank() {
        final String field = "webhook.path";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalidWebhook = new TelegramBotProperties.Webhook("https://example.com", "   ");
        var invalid = new TelegramBotProperties(allowedUpdateTypes, connections, invalidWebhook, token);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when 'token.bytesSize' is below minimum (@Min)")
    void shouldFailValidationTokenBytesSizeTooSmall() {
        final int outOfBoundBytesSize = 8;
        final String field = "token.bytesSize";
        final String messageTemplate = "{jakarta.validation.constraints.Min.message}";
        var invalidToken = new TelegramBotProperties.Token(outOfBoundBytesSize);
        var invalid = new TelegramBotProperties(allowedUpdateTypes, connections, webhook, invalidToken);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
