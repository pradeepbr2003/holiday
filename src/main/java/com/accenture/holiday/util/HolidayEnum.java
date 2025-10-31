package com.accenture.holiday.util;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public enum HolidayEnum {
    MANDATORY_HOLIDAYS("Mandatory"),
    FLOATING_HOLIDAYS("Floating"),
    MANDATORY_HOLIDAY_FOR("Mandatory holiday for "),
    FLOATING_HOLIDAY_FOR("Floating holiday for "),
    BANGALORE_HOLIDAYS_LOG("bangalore_holidays.log"),
    DATE_PATTERN("\\d+-\\D{3}-\\d{4}"),
    FORMAT("%s(%s) :  %s"),
    DATE_FORMAT("dd-MMM-yyyy"),
    WEEK_DAY("EEEE"),
    EMPTY_STR("");

    private String value;
    private SimpleDateFormat sdf;

    HolidayEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }


    public Pattern pattern() {
        return Pattern.compile(value());
    }

    public SimpleDateFormat formatter() {
        if (this.sdf == null) {
            this.sdf = new SimpleDateFormat(value());
        }
        return this.sdf;
    }

    public String message() {
        String message = (value() == MANDATORY_HOLIDAYS.value()) ? MANDATORY_HOLIDAY_FOR.value() :
                (value() == FLOATING_HOLIDAYS.value()) ? FLOATING_HOLIDAY_FOR.value() : this.value;
        return message;
    }

}
