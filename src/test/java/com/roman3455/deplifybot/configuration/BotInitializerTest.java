package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.exception.business.BotInitializationException;
import com.roman3455.deplifybot.service.telegram.TelegramClientService;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("BotInitializer — behavior tests")
class BotInitializerTest {

    private static final List<TelegramBotProperties.LanguageSpec> LANG_SPECS = List.of(
            new TelegramBotProperties.LanguageSpec("und", null, "DEFAULT"),
            new TelegramBotProperties.LanguageSpec("en", "en", "EN"),
            new TelegramBotProperties.LanguageSpec("ru", "ru", "RU")
    );

    private final MessageSource msgSource = mock(MessageSource.class);
    private final TelegramApiTokenConfiguration tokenConfig = mock(TelegramApiTokenConfiguration.class);
    private final TelegramBotProperties botProperties = mock(TelegramBotProperties.class);
    private final TelegramClientService clientService = mock(TelegramClientService.class);

    @Captor
    private ArgumentCaptor<SetWebhookRequest> webhookCaptor;

    @Captor
    private ArgumentCaptor<SetMyCommandsRequest> commandsCaptor;

    @BeforeEach
    void setUp() {
        final int httpConnections = 37;
        when(botProperties.webhookUrl()).thenReturn("https://example.com");
        when(botProperties.webhookPath()).thenReturn("/webhook");
        when(botProperties.allowedHttpConnections()).thenReturn(httpConnections);
        when(botProperties.allowedUpdateTypes()).thenReturn(List.of(UpdateType.MESSAGE, UpdateType.CALLBACK_QUERY));
        when(botProperties.dropPendingUpdates()).thenReturn(true);
        when(botProperties.setUserCommandMenu()).thenReturn(BotCommandScopeType.ALL_PRIVATE_CHATS);
        when(botProperties.languageSpecs()).thenReturn(LANG_SPECS);
        when(tokenConfig.getToken()).thenReturn("secret-token");
        when(msgSource.getMessage(anyString(), isNull(), anyString(), any(Locale.class))).thenReturn("x");

        ResponseBody<Boolean> ok = okTrue();
        when(clientService.setMyDescription(any())).thenReturn(ok);
        when(clientService.setMyShortDescription(any())).thenReturn(ok);
        when(clientService.setMyCommands(any())).thenReturn(ok);
        when(clientService.setWebhook(any())).thenReturn(ok);
    }

    @Test
    @DisplayName("Should initialize bot for all locales successfully")
    void shouldInitializeBotForAllLocales() {
        final int expectedNumbersOfInvocation = 3;
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService, times(expectedNumbersOfInvocation)).setMyDescription(any());
        verify(clientService, times(expectedNumbersOfInvocation)).setMyShortDescription(any());
        verify(clientService, times(expectedNumbersOfInvocation)).setMyCommands(any());
        verify(clientService, times(1)).setWebhook(any());
        verify(tokenConfig, times(1)).getToken();
    }

    @Test
    @DisplayName("Should build webhook request from bot properties")
    void shouldBuildWebhookRequestFromProperties() {
        final int expectedHttpConnections = 37;
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService).setWebhook(webhookCaptor.capture());
        SetWebhookRequest request = webhookCaptor.getValue();
        assertThat(request.url()).isEqualTo("https://example.com/webhook");
        assertThat(request.maxConnections()).isEqualTo(expectedHttpConnections);
        assertThat(request.dropPendingUpdates()).isTrue();
        assertThat(request.secretToken()).isEqualTo("secret-token");
        assertThat(request.allowedUpdates())
                .extracting(UpdateType::getValue)
                .containsExactly("message", "callback_query");
    }

    @Test
    @DisplayName("Should use default 'maxConnections' when field 'allowedHttpConnections' is null")
    void shouldUseDefaultConnectionsWhenNull() {
        final int expectedDefaultHttpConnections = 40;
        when(botProperties.allowedHttpConnections()).thenReturn(null);
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService).setWebhook(webhookCaptor.capture());
        assertThat(webhookCaptor.getValue().maxConnections()).isEqualTo(expectedDefaultHttpConnections);
    }

    @Test
    @DisplayName("Should set 'allowedUpdates' as null when field 'allowedUpdateTypes' is null")
    void shouldSetAllowedUpdatesNullWhenTypesNull() {
        when(botProperties.allowedUpdateTypes()).thenReturn(null);
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService).setWebhook(webhookCaptor.capture());
        assertThat(webhookCaptor.getValue().allowedUpdates()).isNull();
    }

    @Test
    @DisplayName("Should set 'allowedUpdates' as null when field 'allowedUpdateTypes' is empty")
    void shouldSetAllowedUpdatesNullWhenTypesEmpty() {
        when(botProperties.allowedUpdateTypes()).thenReturn(List.of());
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService).setWebhook(webhookCaptor.capture());
        assertThat(webhookCaptor.getValue().allowedUpdates()).isNull();
    }

    @Test
    @DisplayName("Should propagate per-locale 'languageCode' into SetMyCommandsRequest")
    void shouldSetLanguageCodePerLocaleForCommands() {
        final int expectedNumbersOfInvocation = 3;
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService, times(expectedNumbersOfInvocation)).setMyCommands(commandsCaptor.capture());
        assertThat(commandsCaptor.getAllValues())
                .extracting(SetMyCommandsRequest::languageCode)
                .containsExactlyInAnyOrder(null, "en", "ru");
    }

    @Test
    @DisplayName("Should use 'DEFAULT' scope when field 'setUserCommandMenu' is CHAT")
    void shouldUseDefaultScopeWhenChatTypeIsChat() {
        final int expectedNumbersOfInvocation = 3;
        when(botProperties.setUserCommandMenu()).thenReturn(BotCommandScopeType.CHAT);
        BotInitializer initializer = newInitializer();
        initializer.botInit();

        verify(clientService, times(expectedNumbersOfInvocation)).setMyCommands(commandsCaptor.capture());
        assertThat(commandsCaptor.getAllValues()).allSatisfy(req -> {
            Assertions.assertNotNull(req.scope());
            assertThat(req.scope().type()).isEqualTo(BotCommandScopeType.DEFAULT);
        });
    }

    @Test
    @DisplayName("Should throw when webhook returns domain error")
    void shouldThrowWhenWebhookResponseNotOk() {
        final int errorCode = 400;
        when(clientService.setWebhook(any()))
                .thenReturn(new ResponseBody<>(false, true, errorCode, "bad", null));
        BotInitializer initializer = newInitializer();

        assertThatThrownBy(initializer::botInit)
                .isInstanceOf(BotInitializationException.class)
                .hasMessageContaining("Domain error while setting bot webhook");
    }

    @Test
    @DisplayName("Should wrap transport exception during bot description setup")
    void shouldWrapFeignExceptionDuringSetDescription() {
        when(clientService.setMyDescription(any())).thenThrow(mock(FeignException.class));
        BotInitializer initializer = newInitializer();

        assertThatThrownBy(initializer::botInit)
                .isInstanceOf(BotInitializationException.class)
                .hasMessageContaining("Transport error while setting bot bot.description");
    }

    private BotInitializer newInitializer() {
        return new BotInitializer(msgSource, tokenConfig, botProperties, clientService);
    }

    private static ResponseBody<Boolean> okTrue() {
        return new ResponseBody<>(true, true, null, null, null);
    }

}
