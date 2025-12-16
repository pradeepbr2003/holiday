package com.accenture.holiday.service;

import com.accenture.holiday.util.HolidayEnum;
import com.accenture.holiday.util.HolidayFilterUtil;
import com.accenture.holiday.util.HolidayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.accenture.holiday.util.HolidayEnum.*;
import static java.util.Map.Entry.comparingByKey;

/**
 * Service responsible for parsing holiday data and producing structured responses
 * grouped by holiday type and month.
 */
@Service
public class HolidayService {

    @Autowired
    private HolidayUtil holidayUtil;

    @Autowired
    private HolidayFilterUtil holidayFilter;

    /**
     * Converts a raw list of holiday strings into a map keyed by {@link HolidayEnum}
     * containing month grouped holiday messages.
     *
     * @param holidayList raw holiday lines (as loaded from resources)
     * @param htype       requested holiday type filter ("Mandatory", "Floating", or other for all)
     * @param month       month filter (0 to ignore, otherwise 1-12)
     * @return a map where the key is the holiday enum type and the value is a sorted list of
     * Month -> List of holiday message strings
     */
    public Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> holidays(List<String> holidayList, String htype, int month) {
        Map<Date, StringBuffer> holidayMap = new HashMap<>();
        for (String str : holidayList) {
            Date date = holidayUtil.extractDate(str);
            String[] words = str.split(DATE_PATTERN.value());
            if (holidayMap.containsKey(date)) {
                holidayMap.get(date).append(words[0]);
            } else {
                holidayMap.put(date, new StringBuffer(words[0]));
            }
        }
        List<Map.Entry<Date, StringBuffer>> entryList = holidayMap.entrySet().stream()
                .sorted(comparingByKey(Date::compareTo)).toList();
        return resolveHolidays(entryList, htype, month);
    }

    /**
     * Resolves the requested holiday type filter and delegates to month grouping.
     *
     * @param entryList list of date -> message buffers
     * @param htype     requested holiday type filter
     * @param month     month filter to apply
     * @return a map of HolidayEnum to sorted month entries
     */
    private Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> resolveHolidays(List<Map.Entry<Date, StringBuffer>> entryList,
                                                                                   String htype, Integer month) {
        return switch (htype) {
            case String type when type.equalsIgnoreCase(MANDATORY_HOLIDAYS.value()) ->
                    monthGroupList(entryList, MANDATORY_HOLIDAYS, month);
            case String type when type.equalsIgnoreCase(FLOATING_HOLIDAYS.value()) ->
                    monthGroupList(entryList, FLOATING_HOLIDAYS, month);
            default ->
                    Map.of(MANDATORY_HOLIDAYS, monthGroupList(entryList, MANDATORY_HOLIDAYS, month).get(MANDATORY_HOLIDAYS),
                            FLOATING_HOLIDAYS, monthGroupList(entryList, FLOATING_HOLIDAYS, month).get(FLOATING_HOLIDAYS));
        };
    }

    /**
     * Groups entries by month and maps them to holiday message strings using {@link HolidayUtil}.
     *
     * @param entryList stream of date->buffer entries
     * @param he        holiday enum to filter by
     * @param month     month filter
     * @return a map with a single key (he) mapping to a sorted list of month -> holiday messages
     */
    private Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> monthGroupList(List<Map.Entry<Date, StringBuffer>> entryList, HolidayEnum he, int month) {
        Map<Month, List<String>> holidayMap = entryList.stream()
                .filter(e -> holidayFilter.filterHolidays(e, he.value(), month))
                .collect(Collectors.groupingBy(e -> Month.of(e.getKey().getMonth() + 1), Collectors.mapping(ee -> holidayUtil.holidayMessage(ee, MANDATORY_HOLIDAYS), Collectors.toList())));
        return Map.of(he, holidayMap.entrySet().stream().sorted(comparingByKey()).toList());
    }
}
