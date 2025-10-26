package com.roman3455.deplifybot.dto.telegram.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO represents a single bot command in Telegram.
 *
 * @param command     required. Unique name of the command without the leading slash. Must match the pattern
 *                    {@code ^[a-z0-9_]+$} and be between 1 and {@value #MAX_COMMAND_LENGTH} characters long.
 * @param description required. Description of the command displayed to the user. Must be between 1 and
 *                    {@value #MAX_DESCRIPTION_LENGTH} characters long.
 * @see <a href="https://core.telegram.org/bots/api#botcommand">Telegram API — BotCommand</a>
 */
public record MyCommand(

        @NotBlank(message = "Field 'command' is required.")
        @Size(max = MAX_COMMAND_LENGTH, message = "Allowed 'command' length must be at most 32 characters.")
        @Pattern(regexp = "^[a-z0-9_]+$", message = "Only characters 'a-z', '0-9', '_' are allowed.")
        String command,

        @NotBlank(message = "Field 'description' is required.")
        @Size(max = MAX_DESCRIPTION_LENGTH, message = "Allowed 'description' length must be at most 256 characters.")
        String description

) {

    /**
     * The maximum allowed length of the {@code command} field.
     */
    private static final int MAX_COMMAND_LENGTH = 32;

    /**
     * The maximum allowed length of the {@code description} field.
     */
    private static final int MAX_DESCRIPTION_LENGTH = 256;

}
