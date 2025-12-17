package com.accenture.holiday.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayEnumTest {

    @Test
    void value_returnsExpectedString() {
        assertThat(HolidayEnum.MANDATORY_HOLIDAYS.value()).isEqualTo("Mandatory");
        assertThat(HolidayEnum.FLOATING_HOLIDAYS.value()).isEqualTo("Floating");
    }

    @Test
    void pattern_and_formatter_work() {
        assertThat(HolidayEnum.DATE_PATTERN.pattern()).isNotNull();
        assertThat(HolidayEnum.DATE_FORMAT.formatter()).isNotNull();
    }

    @Test
    void message_returnsExpectedPrefix() {
        assertThat(HolidayEnum.MANDATORY_HOLIDAYS.message()).isEqualTo(HolidayEnum.MANDATORY_HOLIDAY_FOR.value());
        assertThat(HolidayEnum.FLOATING_HOLIDAYS.message()).isEqualTo(HolidayEnum.FLOATING_HOLIDAY_FOR.value());
    }
}

