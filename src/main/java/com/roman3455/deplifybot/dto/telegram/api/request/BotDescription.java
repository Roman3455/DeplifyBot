package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * DTO represents a Telegram bot's description.
 *
 * <p>Either {@code description} or {@code languageCode} must be provided (non-null and non-blank).
 * If both fields are {@code null}, an {@link IllegalArgumentException} will be thrown.</p>
 *
 * @param description  optional. New bot description; 0-512 characters {@value #DESCRIPTION_MAX_LENGTH}.
 *                     Pass an empty string to remove the dedicated description for the given language.
 * @param languageCode optional. A two-letter ISO 639-1 language code.
 *                     If empty, the description will be applied to all users for whose language there is no
 *                     dedicated description.
 * @see <a href="https://core.telegram.org/bots/api#botdescription">Telegram API — BotDescription</a>
 * @see <a href="https://core.telegram.org/bots/api#setmydescription">Telegram API — setMyDescription</a>
 */
public record BotDescription(

        @Nullable
        @Size(max = DESCRIPTION_MAX_LENGTH, message = "Allowed 'description' length is between 0 and 512 characters.")
        String description,

        @Nullable
        @ISO6391(message = "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters.")
        String languageCode

) {

    /**
     * The max allowed length of the {@code description} field.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 512;

    /**
     * Validates the {@code BotDescription} record.
     *
     * @throws IllegalArgumentException if both {@code description} and {@code languageCode} are {@code null}.
     */
    public BotDescription {
        if (description == null && languageCode == null) {
            throw new IllegalArgumentException("Field 'description' and 'languageCode' cannot be NULL");
        }
    }

}
