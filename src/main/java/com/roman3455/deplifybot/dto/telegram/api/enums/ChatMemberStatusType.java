package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roman3455.deplifybot.util.enums.JsonEnum;
import com.roman3455.deplifybot.util.enums.JsonEnumUtil;

/**
 * Represents the membership status of a user within a chat, as defined by the Telegram Bot API.
 *
 * <p>Each constant corresponds to a specific access level or state in the chat. The enum maps directly to
 * lowercase values found in JSON payloads. The {@link #UNKNOWN} value provides forward compatibility in
 * case of new status types introduced by Telegram.</p>
 *
 * @see <a href="https://core.telegram.org/bots/api#chatmember">Telegram API — ChatMember</a>
 */
public enum ChatMemberStatusType implements JsonEnum {

    /**
     * The user is an administrator in the chat.
     */
    ADMINISTRATOR("administrator"),

    /**
     * The user is the creator (owner) of the chat.
     */
    CREATOR("creator"),

    /**
     * The user has been kicked from the chat and cannot return.
     */
    KICKED("kicked"),

    /**
     * The user has left the chat.
     */
    LEFT("left"),

    /**
     * The user is a regular member of the chat.
     */
    MEMBER("member"),

    /**
     * The user is restricted and has limited interaction rights.
     */
    RESTRICTED("restricted"),

    /**
     * Fallback for unrecognized or newly introduced membership states.
     */
    UNKNOWN("unknown");

    private final String value;

    ChatMemberStatusType(final String value) {
        this.value = value;
    }

    /**
     * @return the string value used for JSON serialization and matching against Telegram API payloads.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Resolves a {@code ChatMemberStatusType} from its string value.
     *
     * @param value the string value from the incoming JSON payload.
     * @return the corresponding {@code ChatMemberStatusType}, or {@link #UNKNOWN} if no match is found.
     */
    @JsonCreator
    public static ChatMemberStatusType fromValue(final String value) {
        return JsonEnumUtil.fromValue(ChatMemberStatusType.class, value, UNKNOWN);
    }

}
