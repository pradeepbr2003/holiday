package com.accenture.holiday.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.Map;

@Component
public class HolidayFilterUtil {

    public boolean filterHolidays(Map.Entry<Date, StringBuffer> e, String type, int month) {
        return (month == 0) ? filterHolidayType(e, type) : filterSpecificMonthAndType(e, type, month);
    }

    private boolean filterSpecificMonthAndType(Map.Entry<Date, StringBuffer> e, String type, int month) {
        return filterHolidayType(e, type) && ((e.getKey().getMonth() + 1) == month);
    }

    private boolean filterHolidayType(Map.Entry<Date, StringBuffer> e, String type) {
        return e.getValue().toString().contains(type) && filterWeekDay(e.getValue().toString().toUpperCase());
    }

    private boolean filterWeekDay(String value) {
        return !(value.contains(DayOfWeek.SATURDAY.name()) || value.contains(DayOfWeek.SUNDAY.name()));
    }

}
