package com.roman3455.deplifybot.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Servlet filter that protects the Telegram webhook endpoint by validating the
 * {@code X-Telegram-Bot-Api-Secret-Token} header.
 *
 * <p>This filter is applied only to the Telegram webhook path defined in
 * {@link TelegramBotProperties}. Requests whose path does not exactly match the
 * configured webhook endpoint are ignored via {@link #shouldNotFilter(HttpServletRequest)}.
 *
 * <p>For matching requests:
 * <ul>
 *     <li>Extracts the secret header sent by Telegram.</li>
 *     <li>Validates it using {@link TelegramApiTokenConfiguration#matches(String)}.</li>
 *     <li>If validation fails, returns {@code 401 Unauthorized} and logs the event.</li>
 *     <li>If valid, passes the request down the filter chain.</li>
 * </ul>
 *
 * <p>The filter uses the precomputed {@code webhookPath} to avoid repeatedly
 * resolving configuration values at request time and to guarantee stable matching.</p>
 */
@Component
public final class TelegramWebhookAuthFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramWebhookAuthFilter.class);
    private static final String HEADER = "X-Telegram-Bot-Api-Secret-Token";

    private final TelegramApiTokenConfiguration tokenConfiguration;

    private final String webhookPath;

    public TelegramWebhookAuthFilter(
            final TelegramApiTokenConfiguration tokenConfiguration,
            final TelegramBotProperties botProperties
    ) {
        this.tokenConfiguration = tokenConfiguration;
        this.webhookPath = botProperties.webhookPath();
    }

    /**
     * Filters only requests whose URI exactly matches the configured webhook path.
     */
    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        String uri = request.getRequestURI();
        String path = request.getContextPath() + webhookPath;
        return !uri.equals(path);
    }

    /**
     * Performs token validation for Telegram webhook requests. Rejects unauthorized requests
     * and logs details for traceability.
     */
    @Override
    protected void doFilterInternal(
            @NotNull final HttpServletRequest request,
            @NotNull final HttpServletResponse response,
            @NotNull final FilterChain filterChain
    ) throws ServletException, IOException {
        String tokenFromHeader = request.getHeader(HEADER);
        if (!tokenConfiguration.matches(tokenFromHeader)) {
            LOG.warn(
                    "Rejected Telegram webhook request due to invalid API token. uri='{}', remote='{}'",
                    request.getRequestURI(),
                    request.getRemoteAddr()
            );
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

}
