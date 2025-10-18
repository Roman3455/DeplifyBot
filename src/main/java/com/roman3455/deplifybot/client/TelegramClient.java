package com.roman3455.deplifybot.client;

import com.roman3455.deplifybot.configuration.TelegramFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "telegram", configuration = TelegramFeignConfig.class)
public interface TelegramClient {
}
