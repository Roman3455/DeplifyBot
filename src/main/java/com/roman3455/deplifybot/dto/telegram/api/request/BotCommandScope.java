package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents the scope to which bot commands are applied.
 *
 * @param type   required. Scope type {@link BotCommandScopeType}.
 * @param chatId optional. UID for the target chat. Must be provided only if {@code type} is {@code CHAT} or
 *               {@code CHAT_MEMBER}.
 * @see <a href="https://core.telegram.org/bots/api#botcommandscope">Telegram API — BotCommandScope</a>
 */
public record BotCommandScope(

        @NotNull(message = "Field 'type' is required.")
        BotCommandScopeType type,

        @Nullable
        Long chatId

) {

    /**
     * Validates the {@code BotCommandScope} record.
     *
     * @throws IllegalArgumentException if {@code chatId} is set for a type other than 'CHAT'.
     */
    public BotCommandScope {
        if (chatId != null && type != BotCommandScopeType.CHAT) {
            throw new IllegalArgumentException("'chatId' must be provided only if 'type' is 'CHAT'.");
        }
    }

}
