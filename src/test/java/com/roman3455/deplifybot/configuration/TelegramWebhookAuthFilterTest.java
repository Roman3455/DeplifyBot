package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.service.telegram.TelegramApiTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("TelegramWebhookAuthFilter — behavior tests")
class TelegramWebhookAuthFilterTest {

    private static final String HEADER = "X-Telegram-Bot-Api-Secret-Token";
    private static final String PATH = "/telegram/webhook";
    private static final String VALID_TOKEN = "valid-token";
    private static final String INVALID_TOKEN = "invalid-token";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private TelegramBotProperties botProperties;

    @Mock
    private TelegramApiTokenService tokenService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain chain;

    private TelegramWebhookAuthFilter filter;

    @BeforeEach
    void setUp() {
        given(botProperties.webhook().path()).willReturn("telegram/webhook");
        filter = new TelegramWebhookAuthFilter(tokenService, botProperties);
    }

    @Test
    @DisplayName("When valid path and valid token, Then request passes through filter")
    void validPathAndValidTokenAllowsProcessing() throws ServletException, IOException {
        given(request.getContextPath()).willReturn("");
        given(request.getRequestURI()).willReturn(PATH);
        given(request.getHeader(HEADER)).willReturn(VALID_TOKEN);
        given(tokenService.matches(VALID_TOKEN)).willReturn(true);
        filter.doFilter(request, response, chain);
        verify(tokenService, times(1)).matches(VALID_TOKEN);
        verify(chain, times(1)).doFilter(request, response);
        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("When invalid path, Then 'shouldNotFilter()' is true & 'doFilterInternal()' is skipped")
    void invalidPathSkipsFilter() throws ServletException, IOException {
        given(request.getContextPath()).willReturn("");
        given(request.getRequestURI()).willReturn("/other");
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
        verify(tokenService, never()).matches(any());
        verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("When valid path and invalid token, Then return 401 Unauthorized")
    void validPathAndInvalidTokenRejected() throws ServletException, IOException {
        given(request.getContextPath()).willReturn("");
        given(request.getRequestURI()).willReturn(PATH);
        given(request.getHeader(HEADER)).willReturn(INVALID_TOKEN);
        given(tokenService.matches(INVALID_TOKEN)).willReturn(false);
        given(request.getRequestURI()).willReturn(PATH);
        given(request.getRemoteAddr()).willReturn("1.2.3.4");
        filter.doFilter(request, response, chain);
        verify(tokenService, times(1)).matches(INVALID_TOKEN);
        verify(response, times(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(any(), any());
    }

    @Test
    @DisplayName("When valid path and null token, Then return 401 Unauthorized")
    void missingTokenRejected() throws ServletException, IOException {
        given(request.getContextPath()).willReturn("");
        given(request.getRequestURI()).willReturn(PATH);
        given(request.getHeader(HEADER)).willReturn(null);
        given(tokenService.matches(null)).willReturn(false);
        given(request.getRequestURI()).willReturn(PATH);
        given(request.getRemoteAddr()).willReturn("127.0.0.1");
        filter.doFilter(request, response, chain);
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(any(), any());
    }

}
