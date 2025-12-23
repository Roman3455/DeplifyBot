package com.roman3455.deplifybot.service.telegram.impl;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ParseModeType;
import com.roman3455.deplifybot.dto.telegram.api.request.SendMessageRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.Update;
import com.roman3455.deplifybot.service.telegram.TelegramClientService;
import com.roman3455.deplifybot.service.telegram.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public final class TelegramServiceImpl implements TelegramService {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramServiceImpl.class);

    private final TelegramClientService client;

    public TelegramServiceImpl(final TelegramClientService client) {
        this.client = client;
    }

    @Override
    public void processUpdate(final Update update) {
        if (!update.hasMessage() && !update.hasCallbackQuery() && !update.hasMyChatMember()) {
            return;
        }

        if (update.hasMessage()) {
            var message = update.message();
            if (message != null && message.hasText() && message.chat().type().equals(ChatType.PRIVATE)) {
                LOG.info("Updating Telegram {} [{}]", message.text(), message.chat().type().getValue());
                var messageToReturn = new SendMessageRequest(
                        update.message().chat().id(),
                        null,
                        update.message().text(),
                        ParseModeType.PLAIN,
                        true,
                        true,
                        null
                );
                client.sendMessage(messageToReturn);
            }
        }
    }

}
