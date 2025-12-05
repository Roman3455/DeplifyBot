package com.roman3455.deplifybot.dto.telegram.api.response;

import org.springframework.lang.Nullable;

/**
 * DTO represents a generic API response wrapper returned by Telegram Bot API or similar services.
 *
 * <p>The {@code ResponseBody} contains both successful and error responses.</p>
 *
 * @param ok          required. Indicates whether the request was successful.
 * @param result      optional. The actual result object returned by the API if {@code ok} is {@code true}.
 * @param errorCode   optional. The error code returned by the API if {@code ok} is {@code false}.
 * @param description optional. A human-readable description of the error.
 * @param parameters  optional additional response parameters such as retry information or migration targets.
 */
public record ResponseBody<T>(

        boolean ok,

        @Nullable
        T result,

        @Nullable
        Integer errorCode,

        @Nullable
        String description,

        @Nullable
        ResponseParameters parameters

) {

    /**
     * DTO represents optional extra response parameters returned by the API.
     *
     * <p>Typically present for specific error conditions.</p>
     *
     * @param migrateToChatId optional. New chat ID to migrate to, if applicable.
     * @param retryAfter      optional. Number of seconds to wait before retrying, if applicable.
     */
    public record ResponseParameters(

            @Nullable
            Long migrateToChatId,

            @Nullable
            Integer retryAfter

    ) {
    }

    /**
     * @return {@code true} if this response contains error information.
     */
    public boolean isError() {
        return errorCode != null;
    }

    /**
     * @return {@code true} if {@link #parameters} is not {@code null}
     */
    public boolean hasParameters() {
        return parameters != null;
    }

}
