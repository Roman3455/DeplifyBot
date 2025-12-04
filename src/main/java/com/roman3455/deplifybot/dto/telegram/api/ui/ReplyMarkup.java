package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
public sealed interface ReplyMarkup permits
        ForceReply,
        InlineKeyboardMarkup,
        ReplyKeyboardMarkup,
        ReplyKeyboardRemove {
}
