package com.accenture.holiday.util;

import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.Map;

/**
 * Helper that applies type and month filters to holiday entries.
 */
@Component
public class HolidayFilterUtil {

    /**
     * Determines whether a given entry should be included based on the requested
     * holiday type and month.
     *
     * @param e     the date->message entry to evaluate
     * @param type  expected type string (e.g. "Mandatory" or "Floating")
     * @param month month filter (0 to ignore)
     * @return true if the entry should be included, false otherwise
     */
    public boolean filterHolidays(Map.Entry<Date, StringBuffer> e, String type, int month) {
        return (month == 0) ? filterHolidayType(e, type) : filterSpecificMonthAndType(e, type, month);
    }

    /**
     * Applies both month and type filters.
     */
    private boolean filterSpecificMonthAndType(Map.Entry<Date, StringBuffer> e, String type, int month) {
        return filterHolidayType(e, type) && ((e.getKey().getMonth() + 1) == month);
    }

    /**
     * Checks if the entry contains the requested type string and is a weekday.
     */
    private boolean filterHolidayType(Map.Entry<Date, StringBuffer> e, String type) {
        return e.getValue().toString().contains(type) && filterWeekDay(e.getValue().toString().toUpperCase());
    }

    /**
     * Excludes weekends from results based on textual markers in the value string.
     */
    private boolean filterWeekDay(String value) {
        return !(value.contains(DayOfWeek.SATURDAY.name()) || value.contains(DayOfWeek.SUNDAY.name()));
    }

}
