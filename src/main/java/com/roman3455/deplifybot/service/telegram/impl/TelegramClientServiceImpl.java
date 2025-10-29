package com.roman3455.deplifybot.service.telegram.impl;

import com.roman3455.deplifybot.client.TelegramClient;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.service.telegram.TelegramClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Default implementation of {@link TelegramClientService} based on {@link TelegramClient}.
 *
 * <p>This service delegates actual HTTP communication to {@link TelegramClient}
 * and applies basic validation on input DTOs.</p>
 *
 * @see TelegramClientService
 */
@Service
@Validated
public class TelegramClientServiceImpl implements TelegramClientService {

    private final TelegramClient client;

    public TelegramClientServiceImpl(final TelegramClient client) {
        this.client = client;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseBody<Boolean> setMyDescription(@Valid @NotNull final BotDescriptionRequest request) {
        return client.setMyDescription(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseBody<Boolean> setMyShortDescription(@Valid @NotNull final BotShortDescriptionRequest request) {
        return client.setMyShortDescription(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseBody<Boolean> setMyCommands(@Valid @NotNull final SetMyCommandsRequest request) {
        return client.setMyCommands(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseBody<Boolean> setWebhook(@Valid @NotNull final SetWebhookRequest request) {
        return client.setWebhook(request);
    }

}
