package com.accenture.holiday.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;

import static com.accenture.holiday.util.HolidayEnum.*;

@Component
public class HolidayUtil {

    public String holidayMessage(Map.Entry<Date, StringBuffer> e, HolidayEnum he) {
        String dayOfWeek = WEEK_DAY.formatter().format(e.getKey());
        String holidayMsg = String.format(FORMAT.value(), e.getKey().getDate(), dayOfWeek, e.getValue().toString().replaceAll(he.message(), EMPTY_STR.value()));
        return holidayMsg;
    }

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
