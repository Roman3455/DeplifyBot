package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.client.TelegramClient;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.MyCommand;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.exception.telegram.TelegramTooManyRequestsException;
import com.roman3455.deplifybot.service.telegram.impl.TelegramClientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TelegramClientServiceImpl — delegation & exceptions tests")
class TelegramClientServiceTest {

    @Mock
    private TelegramClient client;

    @InjectMocks
    private TelegramClientServiceImpl service;

    @Test
    @DisplayName("Should delegate 'setMyDescription()' to Feign client and return expected response")
    void shouldDelegateSetMyDescriptionToFeignClientAndReturnExpectedResponse() {
        var req = new BotDescriptionRequest("description", "en");
        var expected = new ResponseBody<>(true, true, null, null, null);
        when(client.setMyDescription(req)).thenReturn(expected);
        var actual = service.setMyDescription(req);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyDescription(req);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setMyShortDescription()' to Feign client and return expected response")
    void shouldDelegateSetMyShortDescriptionToClientAndReturnResponse() {
        var req = new BotShortDescriptionRequest("short description", "en");
        var expected = new ResponseBody<>(true, true, null, null, null);
        when(client.setMyShortDescription(req)).thenReturn(expected);
        var actual = service.setMyShortDescription(req);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyShortDescription(req);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setMyCommands()' to Feign client and return expected response")
    void shouldDelegateSetMyCommandsToClientAndReturnResponse() {
        var req = new SetMyCommandsRequest(List.of(new MyCommand("c", "d")), null, null);
        var expected = new ResponseBody<>(true, true, null, null, null);
        when(client.setMyCommands(req)).thenReturn(expected);
        var actual = service.setMyCommands(req);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyCommands(req);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setWebhook()' to Feign client and return expected response")
    void shouldDelegateSetWebhookToClientAndReturnResponse() {
        var req = new SetWebhookRequest("https://ok", null, null, null, null);
        var expected = new ResponseBody<>(true, true, null, null, null);
        when(client.setWebhook(req)).thenReturn(expected);
        var actual = service.setWebhook(req);
        assertThat(actual).isSameAs(expected);
        verify(client).setWebhook(req);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should propagate TelegramTooManyRequestsException from Feign client in 'setMyCommands()'")
    void shouldPropagateTelegramTooManyRequestsExceptionInSetMyCommands() {
        var req = new SetMyCommandsRequest(List.of(), null, null);
        var err = new TelegramTooManyRequestsException("429", 1);
        when(client.setMyCommands(req)).thenThrow(err);
        assertThatThrownBy(() -> service.setMyCommands(req))
                .isSameAs(err);
        verify(client).setMyCommands(req);
        verifyNoMoreInteractions(client);
    }

}
