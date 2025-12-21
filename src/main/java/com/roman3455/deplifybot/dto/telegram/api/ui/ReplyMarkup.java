package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Marker interface for Telegram reply markup DTOs.
 *
 * <p>Reply markup objects define how the Telegram client should modify
 * the message input interface in response to a bot message.</p>
 *
 * <p>Depending on the implementation, reply markup may:</p>
 * <ul>
 *   <li>display an inline keyboard ({@link InlineKeyboardMarkup}),</li>
 *   <li>display a custom reply keyboard ({@link ReplyKeyboardMarkup}),</li>
 *   <li>remove the current reply keyboard ({@link ReplyKeyboardRemove}),</li>
 *   <li>force the user to reply to a message ({@link ForceReply}).</li>
 * </ul>
 *
 * <p>Specification:
 * <a href="https://core.telegram.org/bots/api#replykeyboardmarkup">Telegram Bot API — Reply Markup</a>
 * </p>
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ReplyMarkup permits
        ForceReply,
        InlineKeyboardMarkup,
        ReplyKeyboardMarkup,
        ReplyKeyboardRemove {
}
