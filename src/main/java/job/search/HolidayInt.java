package job.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static job.search.helper.GeneralEnum.*;
import static job.search.helper.RegExEnum.DATE_PATTERN;
import static job.search.helper.RegExEnum.DATE_REG_EX;

public interface HolidayInt {
    static void extractHoliday(Map<Date, Set<String>> holidayMap) {
        try {
            List<String> contents = Files.readAllLines(Paths.get(HOLIDAYS_LOG.value()));
            contents.forEach(content -> {
                extractHoliday(content, holidayMap);
            });
        } catch (IOException e) {
            System.out.printf("%n %s %n", e.getMessage());
        }
    }

    static void extractHoliday(String content, Map<Date, Set<String>> holidayMap) {
        Matcher matcher = DATE_REG_EX.pattern().matcher(content);
        if (matcher.find()) {
            String dateStr = matcher.group();
            try {
                Date date = DATE_PATTERN.formatter().parse(dateStr);
                String holidayReason = content.replaceAll(DATE_REG_EX.value(), EMPTY_STR.value());
                if (holidayMap.containsKey(date)) {
                    holidayMap.get(date).add(holidayReason);
                } else {
                    holidayMap.put(date, new HashSet<>(List.of(holidayReason)));
                }
            } catch (ParseException e) {
                System.out.printf("%n Exception : %s %n", e.getMessage());
            }
        }
    }

    static boolean isWeekDayAndType(Map.Entry<Date, Set<String>> e, String holidayType) {
        boolean flag = e.getValue().stream().noneMatch(str -> (str.contains(SATURDAY.value()) || str.contains(SUNDAY.value()) || !str.contains(holidayType)));
        if (flag) {
            e.setValue(e.getValue().stream().map(str -> str.replaceAll(holidayType, EMPTY_STR.value()))
                    .collect(Collectors.toSet()));
            return true;
        }
        return false;
    }

    static void printHolidays(Map<Date, Set<String>> holidayMap, String holidayType) {
        System.out.println("-----------------------------------------------------");
        System.out.printf("%n %s %n", holidayType);
        System.out.println("-----------------------------------------------------");
        Map<Integer, List<Map.Entry<Date, Set<String>>>> holidays = holidayMap.entrySet().stream()
                .filter(e -> isWeekDayAndType(e, holidayType))
                .collect(Collectors.groupingBy(e -> e.getKey().getMonth()));
        holidays.forEach((k, v) -> {
            System.out.printf("%n Month - %s %n", Month.values()[k].name());
            System.out.println("------------------------");
            v.stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
                System.out.printf("Date : %d   Reason : %s %n", e.getKey().getDate(), e.getValue());
            });

        });
    }
}
