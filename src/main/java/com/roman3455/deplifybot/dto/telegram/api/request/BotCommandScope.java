package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents the scope to which bot commands are applied.
 *
 * @param type   required. Scope type {@link BotCommandScopeType}.
 * @param chatId optional. UID for the target chat. Must be provided only if {@code type} is {@code CHAT}. If not,
 *               violation will be reported by {@link #isChatIdConsistentWithType()}.
 * @see <a href="https://core.telegram.org/bots/api#botcommandscope">Telegram API — BotCommandScope</a>
 */
public record BotCommandScope(

        @NotNull
        BotCommandScopeType type,

        @Nullable
        Long chatId

) {

    /**
     * Cross-field constraint: require a {@code chatId} field only if {@code type} is {@code CHAT}.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if {@code type} is {@code CHAT} and {@code chatId} is not {@code null}.
     */
    @AssertTrue(message = "{BotCommandScope.isChatIdConsistentWithType.AssertTrue}")
    private boolean isChatIdConsistentWithType() {
        return (chatId != null && type == BotCommandScopeType.CHAT)
                || (chatId == null && type != BotCommandScopeType.CHAT);
    }

}
