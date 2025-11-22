package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.dto.telegram.api.response.Update;

public interface TelegramService {

    void processUpdate(Update update);

}
