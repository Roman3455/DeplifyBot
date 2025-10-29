package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Internal service interface for managing Telegram Bot configuration.
 *
 * <p>This service provides high-level operations for setting bot metadata
 * (description, short description, commands, and webhook settings).
 * It delegates actual HTTP requests to TelegramClient.</p>
 *
 * <p>All DTOs are validated before sending to the Telegram API.</p>
 *
 * @see com.roman3455.deplifybot.client.TelegramClient
 */
public interface TelegramClientService {

    /**
     * Updates the bot description.
     *
     * @param request the validated description request.
     * @return response indicating success or failure.
     */
    ResponseBody<Boolean> setMyDescription(@Valid @NotNull BotDescriptionRequest request);

    /**
     * Updates the bot short description.
     *
     * @param request the validated short description request.
     * @return response indicating success or failure.
     */
    ResponseBody<Boolean> setMyShortDescription(@Valid @NotNull BotShortDescriptionRequest request);

    /**
     * Updates the list of bot commands.
     *
     * @param request the validated commands request.
     * @return response indicating success or failure.
     */
    ResponseBody<Boolean> setMyCommands(@Valid @NotNull SetMyCommandsRequest request);

    /**
     * Configures the bot webhook.
     *
     * @param request the validated webhook request.
     * @return response indicating success or failure.
     */
    ResponseBody<Boolean> setWebhook(@Valid @NotNull SetWebhookRequest request);

}
