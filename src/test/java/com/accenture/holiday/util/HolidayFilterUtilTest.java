package com.accenture.holiday.util;

import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayFilterUtilTest {

    private final HolidayFilterUtil util = new HolidayFilterUtil();

    private Date date(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Test
    void filterWeekDay_excludesWeekends() {
        StringBuffer buf = new StringBuffer("Mandatory holiday for Foo SATURDAY");
        Map.Entry<Date, StringBuffer> entry = new AbstractMap.SimpleEntry<>(date(2025,1,1), buf);
        boolean include = util.filterHolidays(entry, "Mandatory", 0);
        assertThat(include).isFalse();
    }

    @Test
    void filterHolidayType_detectsType() {
        StringBuffer buf = new StringBuffer("Mandatory holiday for Foo MONDAY");
        Map.Entry<Date, StringBuffer> entry = new AbstractMap.SimpleEntry<>(date(2025,1,1), buf);
        boolean include = util.filterHolidays(entry, "Mandatory", 0);
        assertThat(include).isTrue();
    }

    @Test
    void filterSpecificMonthAndType_respectsMonthAndType() {
        StringBuffer buf = new StringBuffer("Mandatory holiday for Foo MONDAY");
        Map.Entry<Date, StringBuffer> entry = new AbstractMap.SimpleEntry<>(date(2025,2,15), buf);
        boolean include = util.filterHolidays(entry, "Mandatory", 2);
        assertThat(include).isTrue();
        boolean exclude = util.filterHolidays(entry, "Mandatory", 3);
        assertThat(exclude).isFalse();
    }
}

