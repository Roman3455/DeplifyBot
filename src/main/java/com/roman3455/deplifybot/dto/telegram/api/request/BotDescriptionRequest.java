package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * DTO represents a Telegram bot's description.
 *
 * <p>Either {@code description} or {@code languageCode} must be provided (non-null and non-blank).
 * If not, violation will be reported by {@link #isAnyProvided()}.</p>
 *
 * @param description  optional. New bot description; 0-512 characters {@value #DESCRIPTION_MAX_LENGTH}.
 *                     Pass an empty string to remove the dedicated description for the given language.
 * @param languageCode optional. A two-letter ISO 639-1 language code.
 *                     If empty, the description will be applied to all users for whose language there is no
 *                     dedicated description.
 * @see <a href="https://core.telegram.org/bots/api#botdescription">Telegram API — BotDescription</a>
 * @see <a href="https://core.telegram.org/bots/api#setmydescription">Telegram API — setMyDescription</a>
 */
public record BotDescriptionRequest(

        @Nullable
        @Size(max = DESCRIPTION_MAX_LENGTH, message = "{Size.max.message}")
        String description,

        @Nullable
        @ISO6391(message = "{ISO6391.languageCode.message}")
        String languageCode

) {

    /**
     * The max allowed length of the {@code description} field.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 512;

    /**
     * Cross-field constraint: require at least one field to be provided.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if {@code description} or {@code languageCode} is not {@code null}.
     */
    @AssertTrue(message = "{BotDescriptionRequest.isAnyProvided.AssertTrue}")
    private boolean isAnyProvided() {
        return description != null || languageCode != null;
    }

}
