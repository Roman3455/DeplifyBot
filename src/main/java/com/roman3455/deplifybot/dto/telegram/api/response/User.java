package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents a Telegram user or bot account.
 *
 * <p>It contains the unique identifier of the user, basic profile information, and an optional language code.
 * The {@code isBot} flag distinguishes regular users from automated bot accounts. The language code, when present,
 * follows the ISO 639-1 standard and can be used for localization decisions.</p>
 *
 * @param id           required. Unique identifier for this user or bot.
 * @param isBot        required. Indicates whether this account belongs to a bot.
 * @param firstName    required. The user’s first name as displayed in their Telegram profile.
 * @param username     optional. The Telegram username, without the leading {@code @}. Present only if the user has
 *                     chosen to set it.
 * @param languageCode optional. The user’s language in ISO 639-1 format (e.g., {@code "en"}, {@code "ru"}).
 *                     May be used for UI localization or message translations.
 * @see <a href="https://core.telegram.org/bots/api#user">Telegram API — User</a>
 */
public record User(

        @NotNull
        Long id,

        boolean isBot,

        @NotNull
        String firstName,

        @Nullable
        String username,

        @Nullable
        @ISO6391
        String languageCode

) {
}
