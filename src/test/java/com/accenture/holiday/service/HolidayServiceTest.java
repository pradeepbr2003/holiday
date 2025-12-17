package com.accenture.holiday.service;

import com.accenture.holiday.util.HolidayEnum;
import com.accenture.holiday.util.HolidayFilterUtil;
import com.accenture.holiday.util.HolidayUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolidayServiceTest {

    @InjectMocks
    private HolidayService holidayService;

    @Mock
    private HolidayUtil holidayUtil;

    @Mock
    private HolidayFilterUtil holidayFilter;

    private Date date(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    @Test
    void holidays_returnsOnlyMandatory_whenHtypeMandatory() {
        List<String> lines = new ArrayList<>();
        lines.add("Mandatory holiday for Alpha 01-Jan-2024");
        lines.add("Floating holiday for Beta 02-Feb-2025");

        // stub extractDate for each input
        when(holidayUtil.extractDate(lines.get(0))).thenReturn(date(2024,1,1));
        when(holidayUtil.extractDate(lines.get(1))).thenReturn(date(2025,2,2));

        // filter should return true for Mandatory (only this is needed for the test)
        when(holidayFilter.filterHolidays(any(), eq(HolidayEnum.MANDATORY_HOLIDAYS.value()), eq(0))).thenReturn(true);

        // return messages for mandatory entries
        when(holidayUtil.holidayMessage(any(), eq(HolidayEnum.MANDATORY_HOLIDAYS))).thenReturn("AlphaMsg");

        Map<HolidayEnum, List<Map.Entry<java.time.Month, List<String>>>> result = holidayService.holidays(lines, "Mandatory", 0);
        assertThat(result).containsKey(HolidayEnum.MANDATORY_HOLIDAYS);
        assertThat(result.get(HolidayEnum.MANDATORY_HOLIDAYS)).isNotEmpty();
    }

    @Test
    void holidays_sortsByDate() {
        List<String> lines = new ArrayList<>();
        lines.add("Mandatory holiday for First 10-Mar-2023");
        lines.add("Mandatory holiday for Second 01-Jan-2024");
        lines.add("Mandatory holiday for Third 05-Feb-2025");

        when(holidayUtil.extractDate(lines.get(0))).thenReturn(date(2023,3,10));
        when(holidayUtil.extractDate(lines.get(1))).thenReturn(date(2024,1,1));
        when(holidayUtil.extractDate(lines.get(2))).thenReturn(date(2025,2,5));

        when(holidayFilter.filterHolidays(any(), any(), any(Integer.class))).thenReturn(true);
        when(holidayUtil.holidayMessage(any(), any())).thenReturn("msg");

        Map<HolidayEnum, List<Map.Entry<java.time.Month, List<String>>>> result = holidayService.holidays(lines, "Mandatory", 0);
        List<Map.Entry<java.time.Month, List<String>>> entries = result.get(HolidayEnum.MANDATORY_HOLIDAYS);
        List<java.time.Month> months = entries.stream().map(Map.Entry::getKey).collect(Collectors.toList());
        // Expect months sorted: JAN, FEB, MAR
        assertThat(months).containsExactly(java.time.Month.JANUARY, java.time.Month.FEBRUARY, java.time.Month.MARCH);
    }
}
