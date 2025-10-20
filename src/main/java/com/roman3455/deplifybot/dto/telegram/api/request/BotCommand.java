package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;

/**
 * DTO represents a request to set a list of bot commands in Telegram.
 *
 * <p>Includes the commands to set, the optional scope for which they apply, and an optional language code.</p>
 *
 * @param commands     required. A list of bot commands to be set as the list of the bot's commands. At most 100
 *                     commands can be specified.
 * @param scope        optional. Describing scope of users for which the commands are relevant. Defaults to
 *                     {@link com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType#DEFAULT}.
 * @param languageCode optional. A two-letter ISO 639-1 language code.
 *                     If empty, the description will be applied to all users for whose language there is no
 *                     dedicated description.
 * @see <a href="https://core.telegram.org/bots/api#setmycommands">Telegram API — setMyCommands</a>
 */
public record BotCommand(

        @NotEmpty(message = "Field 'commands' is required and cannot be empty.")
        @Size(max = MAX_COMMANDS_SIZE, message = "At most 100 commands allowed.")
        List<@NotEmpty Map<String, String>> commands,

        @Nullable
        BotCommandScope scope,

        @Nullable
        @ISO6391(message = "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters.")
        String languageCode

) {

    /**
     * The max allowed length of the {@code description} field.
     */
    private static final int MAX_COMMANDS_SIZE = 100;

}
