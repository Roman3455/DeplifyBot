package com.roman3455.deplifybot.configuration.jackson;

import java.time.LocalDate;

record SampleObject(
        String firstName,
        LocalDate createdAt,
        String nullable,
        TestEnum status
) {

    static final int YEAR = 2025;

    static SampleObject createSampleObject() {
        return new SampleObject("John", LocalDate.of(YEAR, 1, 1), null, TestEnum.VALUE);
    }

}
