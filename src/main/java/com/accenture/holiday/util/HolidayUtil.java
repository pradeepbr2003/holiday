package com.accenture.holiday.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;

import static com.accenture.holiday.util.HolidayEnum.*;

/**
 * Utility methods used to parse holiday lines and format holiday messages.
 */
@Component
public class HolidayUtil {

    /**
     * Produces a formatted holiday message for a given date->message entry and holiday enum.
     * The resulting message has the configured format and removes the enum-specific prefix
     * from the raw message.
     *
     * @param e  entry mapping a Date to the raw message buffer
     * @param he holiday enum used to determine prefixes and message formatting
     * @return a formatted holiday string
     */
    public String holidayMessage(Map.Entry<Date, StringBuffer> e, HolidayEnum he) {
        String dayOfWeek = WEEK_DAY.formatter().format(e.getKey());
        String holidayMsg = String.format(FORMAT.value(), e.getKey().getDate(), dayOfWeek, e.getValue().toString().replaceAll(he.message(), EMPTY_STR.value()));
        return holidayMsg;
    }

    /**
     * Extracts a Date from a raw holiday line using the configured DATE_PATTERN.
     *
     * @param str raw holiday line containing a date like "01-Jan-2025"
     * @return parsed Date instance
     * @throws RuntimeException if the date cannot be parsed or is not found
     */
    public Date extractDate(String str) {
        Matcher matcher = DATE_PATTERN.pattern().matcher(str);
        if (matcher.find()) {
            try {
                return DATE_FORMAT.formatter().parse(matcher.group());
            } catch (ParseException e) {
                throw new RuntimeException(e.getMessage());
            }
        } else {
            throw new RuntimeException("Date not found");
        }
    }
}
