package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

/**
 * DTO represents a Telegram bot's short description.
 *
 * <p>Either {@code shortDescription} or {@code languageCode} must be provided (non-null and non-blank).
 * If not, violation will be reported by {@link #isAnyProvided()}.</p>
 *
 * @param shortDescription optional. New short description for the bot; 0-120 characters
 *                         {@value #SHORT_DESCRIPTION_MAX_LENGTH}. Pass an empty string to remove the dedicated short
 *                         description for the given language.
 * @param languageCode     optional. A two-letter ISO 639-1 language code.
 *                         If empty, the description will be applied to all users for whose language there is no
 *                         dedicated description.
 * @see <a href="https://core.telegram.org/bots/api#botshortdescription">Telegram API — BotShortDescription</a>
 * @see <a href="https://core.telegram.org/bots/api#setmyshortdescription">Telegram API — setMyShortDescription</a>
 */
public record BotShortDescriptionRequest(

        @Nullable
        @Size(max = SHORT_DESCRIPTION_MAX_LENGTH, message = "{Size.max.message}")
        String shortDescription,
        @Nullable
        @ISO6391(message = "{ISO6391.languageCode.message}")
        String languageCode

) {

    /**
     * The max allowed length of the {@code shortDescription} field.
     */
    private static final int SHORT_DESCRIPTION_MAX_LENGTH = 120;

    /**
     * Cross-field constraint: require at least one field to be provided.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if {@code shortDescription} or {@code languageCode} is not {@code null}.
     */
    @AssertTrue(message = "{BotShortDescriptionRequest.isAnyProvided.AssertTrue}")
    private boolean isAnyProvided() {
        return shortDescription != null || languageCode != null;
    }

}
