package com.accenture.holiday.util;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HolidayUtilTest {

    private final HolidayUtil util = new HolidayUtil();

    @Test
    void extractDate_parsesValidDate() {
        String line = "Mandatory holiday for Foo 01-Jan-2025";
        Date d = util.extractDate(line);
        assertThat(d).isNotNull();
    }

    @Test
    void extractDate_throws_whenNoDateFound() {
        String line = "No date here";
        assertThrows(RuntimeException.class, () -> util.extractDate(line));
    }

    @Test
    void holidayMessage_formatsCorrectly() {
        // prepare a buffer with the prefix
        StringBuffer buf = new StringBuffer(HolidayEnum.MANDATORY_HOLIDAY_FOR.value() + "Foo");
        Map.Entry<Date, StringBuffer> entry = new AbstractMap.SimpleEntry<>(util.extractDate("Mandatory holiday for Foo 01-Jan-2025"), buf);
        String msg = util.holidayMessage(entry, HolidayEnum.MANDATORY_HOLIDAYS);
        assertThat(msg).contains("Foo");
        assertThat(msg).contains("(");
    }
}
