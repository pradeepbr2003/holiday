package job.search;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static job.search.HolidayInt.extractHoliday;
import static job.search.HolidayInt.printHolidays;
import static job.search.helper.GeneralEnum.FLOATING_HOLIDAY;
import static job.search.helper.GeneralEnum.MANDATORY_HOLIDAY;

public class Holiday {
    public static void main(String[] args) {
        Map<Date, Set<String>> holidayMap = new HashMap<>();

        extractHoliday(holidayMap);

        printHolidays(holidayMap, MANDATORY_HOLIDAY.value());
        printHolidays(holidayMap, FLOATING_HOLIDAY.value());
    }

}