package com.roman3455.deplifybot.client;

import com.roman3455.deplifybot.configuration.TelegramFeignConfig;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client for interacting with the Telegram Bot API.
 *
 * <p>All methods require valid request DTOs that comply with Telegram Bot API constraints.
 * If the request is invalid, a 4xx error may be returned from the Telegram side.</p>
 *
 * @see <a href="https://core.telegram.org/bots/api#available-methods">Telegram Bot API — Available methods</a>
 */
@FeignClient(name = "telegram", configuration = TelegramFeignConfig.class)
public interface TelegramClient {

    /**
     * Sets the bot’s description shown in the chat with the bot.
     *
     * @param request the {@link BotDescriptionRequest} containing the description and optional language code.
     * @return a {@link ResponseBody} with a boolean indicating whether the operation succeeded.
     * @see <a href="https://core.telegram.org/bots/api#setmydescription">Telegram API — setMyDescription</a>
     */
    @PostMapping(value = "/setMyDescription", consumes = "application/json", produces = "application/json")
    ResponseBody<Boolean> setMyDescription(@RequestBody @Valid BotDescriptionRequest request);

    /**
     * Sets the bot’s short description, which is displayed on the bot profile page and in the list of bots.
     *
     * @param request the {@link BotShortDescriptionRequest} containing the short description and optional
     *                language code.
     * @return a {@link ResponseBody} with a boolean indicating whether the operation succeeded
     * @see <a href="https://core.telegram.org/bots/api#setmyshortdescription">Telegram API — setMyShortDescription</a>
     */
    @PostMapping(value = "/setMyShortDescription", consumes = "application/json", produces = "application/json")
    ResponseBody<Boolean> setMyShortDescription(@RequestBody @Valid BotShortDescriptionRequest request);

    /**
     * Sets the list of bot commands. Commands are shown as a menu to users.
     *
     * @param request the {@link SetMyCommandsRequest} containing commands, scope, and optional language code.
     * @return a {@link ResponseBody} with a boolean indicating whether the operation succeeded.
     * @see <a href="https://core.telegram.org/bots/api#setmycommands">Telegram API — setMyCommands</a>
     */
    @PostMapping(value = "/setMyCommands", consumes = "application/json", produces = "application/json")
    ResponseBody<Boolean> setMyCommands(@RequestBody @Valid SetMyCommandsRequest request);

    /**
     * Configures a webhook URL to receive incoming updates for the bot.
     *
     * @param request the {@link SetWebhookRequest} containing webhook configuration.
     * @return a {@link ResponseBody} with a boolean indicating whether the operation succeeded.
     * @see <a href="https://core.telegram.org/bots/api#setwebhook">Telegram API — setWebhook</a>
     */
    @PostMapping(value = "/setWebhook", consumes = "application/json", produces = "application/json")
    ResponseBody<Boolean> setWebhook(@RequestBody @Valid SetWebhookRequest request);

}
