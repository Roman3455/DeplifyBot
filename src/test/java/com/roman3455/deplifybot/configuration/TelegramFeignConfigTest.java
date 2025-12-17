package com.roman3455.deplifybot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roman3455.deplifybot.exception.TelegramErrorDecoder;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TelegramFeignConfig — object mapper behavior")
class TelegramFeignConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withBean(ObjectMapper.class, ObjectMapper::new)
            .withUserConfiguration(TelegramFeignConfig.class);

    @Test
    @DisplayName("Should provide TelegramErrorDecoder bean when ObjectMapper is present")
    void shouldProvideTelegramErrorDecoderBean() {
        contextRunner.run(ctx -> {
            assertThat(ctx).hasSingleBean(TelegramErrorDecoder.class);
            assertThat(ctx).hasSingleBean(ErrorDecoder.class);
            ErrorDecoder asInterface = ctx.getBean(ErrorDecoder.class);
            TelegramErrorDecoder asImpl = ctx.getBean(TelegramErrorDecoder.class);
            assertThat(asInterface).isSameAs(asImpl);
        });
    }

    @Test
    @DisplayName("Should fail to start context when no ObjectMapper bean is available")
    void shouldFailToStartWithoutObjectMapper() {
        new ApplicationContextRunner()
                .withUserConfiguration(TelegramFeignConfig.class)
                .run(ctx -> {
                    assertThat(ctx).hasFailed();
                    assertThat(ctx.getStartupFailure())
                            .hasRootCauseInstanceOf(NoSuchBeanDefinitionException.class);
                });
    }

}
