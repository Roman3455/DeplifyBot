package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.util.List;

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
public record SetMyCommandsRequest(

        @NotEmpty
        @Size(max = MAX_COMMANDS_AMOUNT, message = "{Size.max.message}")
        List<@Valid MyCommand> commands,

        @Nullable
        @Valid BotCommandScope scope,

        @Nullable
        @ISO6391
        String languageCode

) {

    /**
     * The maximum allowed number of commands in a single request.
     */
    private static final int MAX_COMMANDS_AMOUNT = 100;

    public SetMyCommandsRequest {
        if (isProvided()) {
            commands = List.copyOf(commands.stream()
                    .distinct()
                    .toList());
        }
    }

    /**
     * Cross-field constraint: require not null field to be provided.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if {@code commands} is not {@code null}.
     */
    @AssertTrue(message = "{SetMyCommandsRequest.isProvided.AssertTrue}")
    public boolean isProvided() {
        return commands != null && !commands.isEmpty();
    }

}
