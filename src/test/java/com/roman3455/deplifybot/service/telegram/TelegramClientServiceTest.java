package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.client.TelegramClient;
import com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.exception.telegram.TelegramTooManyRequestsException;
import com.roman3455.deplifybot.service.telegram.impl.TelegramClientServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("TelegramClientServiceImpl — delegation & exceptions tests")
class TelegramClientServiceTest {

    private final TelegramClient client = mock(TelegramClient.class);
    private final TelegramClientServiceImpl service = new TelegramClientServiceImpl(client);

    @Test
    @DisplayName("Should delegate 'setMyDescription()' to Feign client and return expected response")
    void shouldDelegateSetMyDescriptionToFeignClientAndReturnExpectedResponse() {
        var request = TelegramApiDtoBuilder.validBotDescriptionRequestFullPayload();
        var expected = getExpectedResponse(true);
        when(client.setMyDescription(request)).thenReturn(expected);
        var actual = service.setMyDescription(request);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyDescription(request);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setMyShortDescription()' to Feign client and return expected response")
    void shouldDelegateSetMyShortDescriptionToClientAndReturnResponse() {
        var request = TelegramApiDtoBuilder.validBotShortDescriptionRequestFullPayload();
        var expected = getExpectedResponse(true);
        when(client.setMyShortDescription(request)).thenReturn(expected);
        var actual = service.setMyShortDescription(request);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyShortDescription(request);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setMyCommands()' to Feign client and return expected response")
    void shouldDelegateSetMyCommandsToClientAndReturnResponse() {
        var request = TelegramApiDtoBuilder.validSetMyCommandsRequestRequiredPayload();
        var expected = getExpectedResponse(true);
        when(client.setMyCommands(request)).thenReturn(expected);
        var actual = service.setMyCommands(request);
        assertThat(actual).isSameAs(expected);
        verify(client).setMyCommands(request);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should propagate TelegramTooManyRequestsException from Feign client in 'setMyCommands()'")
    void shouldPropagateTelegramTooManyRequestsExceptionInSetMyCommands() {
        var request = TelegramApiDtoBuilder.validSetMyCommandsRequestRequiredPayload();
        var exception = new TelegramTooManyRequestsException("429", 1);
        when(client.setMyCommands(request)).thenThrow(exception);
        assertThatThrownBy(() -> service.setMyCommands(request)).isSameAs(exception);
        verify(client).setMyCommands(request);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'setWebhook()' to Feign client and return expected response")
    void shouldDelegateSetWebhookToClientAndReturnResponse() {
        var request = TelegramApiDtoBuilder.validSetWebhookRequestRequiredPayload();
        var expected = getExpectedResponse(true);
        when(client.setWebhook(request)).thenReturn(expected);
        var actual = service.setWebhook(request);
        assertThat(actual).isSameAs(expected);
        verify(client).setWebhook(request);
        verifyNoMoreInteractions(client);
    }

    @Test
    @DisplayName("Should delegate 'sendMessage()' to Feign client and return expected response")
    void shouldDelegateSendMessageToClientAndReturnResponse() {
        var request = TelegramApiDtoBuilder.validSendMessageRequestRequiredPayload();
        var expected = getExpectedResponse(TelegramApiDtoBuilder.validMessageRequiredPayload());
        when(client.sendMessage(request)).thenReturn(expected);
        var actual = service.sendMessage(request);
        assertThat(actual).isSameAs(expected);
        verify(client).sendMessage(request);
        verifyNoMoreInteractions(client);
    }

    private static <T> ResponseBody<T> getExpectedResponse(final T result) {
        return new ResponseBody<>(true, result, null, null, null);
    }

}
