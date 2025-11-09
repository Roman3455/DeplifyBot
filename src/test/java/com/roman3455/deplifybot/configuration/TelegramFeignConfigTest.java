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
    @DisplayName("Provides TelegramErrorDecoder bean when ObjectMapper is present")
    void providesTelegramErrorDecoderBean() {
        contextRunner.run(ctx -> {
            assertThat(ctx).hasSingleBean(ErrorDecoder.class);
            ErrorDecoder decoder = ctx.getBean(ErrorDecoder.class);
            assertThat(decoder).isInstanceOf(TelegramErrorDecoder.class);
            ErrorDecoder decoder2 = ctx.getBean(ErrorDecoder.class);
            assertThat(decoder2).isSameAs(decoder);
        });
    }

    @Test
    @DisplayName("Context fails when no ObjectMapper bean is available")
    void failsToStartWithoutObjectMapper() {
        new ApplicationContextRunner()
                .withUserConfiguration(TelegramFeignConfig.class)
                .run(ctx -> {
                    assertThat(ctx).hasFailed();
                    assertThat(ctx).getFailure()
                            .hasRootCauseInstanceOf(NoSuchBeanDefinitionException.class);
                });
    }

}
