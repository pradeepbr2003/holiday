package job.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static job.search.EnumMain.*;

public interface IMain {
    static void extractHoliday(Map<Date, Set<String>> mhMap, Map<Date, Set<String>> fhMap) {
        try {
            List<String> contents = Files.readAllLines(Paths.get(HOLIDAYS_LOG.value()));
            contents.forEach(content -> {
                if (content.contains(MANDATORY_HOLIDAY.value())) {
                    extractHoliday(content, mhMap);
                } else {
                    extractHoliday(content, fhMap);
                }
            });
        } catch (IOException e) {
            System.out.printf("%n %s %n", e.getMessage());
        }
    }

    static void extractHoliday(String content, Map<Date, Set<String>> holidayMap) {
        Matcher matcher = DATE_REG_EX.datePattern().matcher(content);
        Matcher reasonMatcher = HOLIDAY_REASON_REG_EX.reasonPattern().matcher(content);
        if (matcher.find() && reasonMatcher.find()) {
            String dateStr = matcher.group();
            String reasonStr = reasonMatcher.group();
            try {
                Date date = DATE_PATTERN_STR.sdf().parse(dateStr);
                if (holidayMap.containsKey(date)) {
                    holidayMap.get(date).add(reasonStr);
                } else {
                    holidayMap.put(date, new HashSet<>(List.of(reasonStr)));
                }
            } catch (ParseException e) {
                System.out.printf("%n Exception : %s %n", e.getMessage());
            }
        }
    }

    static boolean isWeenEnd(Map.Entry<Date, Set<String>> e) {
        for (String str : e.getValue()) {
            if (str.contains(SATURDAY.value()) || str.contains(SUNDAY.value())) return true;
        }
        return false;
    }

    static void printHolidays(Map<Date, Set<String>> holidayMap, String message) {
        System.out.println("-----------------------------------------------------");
        System.out.printf("%n %s %n", message);
        System.out.println("-----------------------------------------------------");
        Map<Integer, List<Map.Entry<Date, Set<String>>>> holidays = holidayMap.entrySet().stream()
                .filter(e -> !isWeenEnd(e))
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
