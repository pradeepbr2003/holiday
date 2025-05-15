package job.search;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static job.search.EnumMain.FLOATING_HOLIDAYS;
import static job.search.EnumMain.MANDATORY_HOLIDAYS;
import static job.search.IMain.extractHoliday;
import static job.search.IMain.printHolidays;

public class Main {
    public static void main(String[] args) {
        Map<Date, Set<String>> mandateHolidayMap = new HashMap<>();
        Map<Date, Set<String>> floatingHolidayMap = new HashMap<>();

        extractHoliday(mandateHolidayMap, floatingHolidayMap);

        printHolidays(mandateHolidayMap, MANDATORY_HOLIDAYS.value());
        printHolidays(floatingHolidayMap, FLOATING_HOLIDAYS.value());
    }

}