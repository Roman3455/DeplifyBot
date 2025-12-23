package com.roman3455.deplifybot.api;

import com.roman3455.deplifybot.dto.telegram.api.response.Update;
import com.roman3455.deplifybot.service.telegram.TelegramService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${telegram.bot.settings.webhook-path}")
public final class TelegramWebhookController {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramWebhookController.class);

    private final TelegramService telegramService;

    public TelegramWebhookController(final TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> receiveUpdate(final @RequestBody @Valid Update update) {
        LOG.info("Received update [{}]", update.updateId());
        telegramService.processUpdate(update);
        return ResponseEntity.ok().build();
    }

}
