package com.accenture.holiday.controller;

import com.accenture.holiday.service.HolidayService;
import com.accenture.holiday.util.HolidayEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Month;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolidayControllerTest {

    @InjectMocks
    private HolidayController controller;

    @Mock
    private HolidayService holidayService;

    @BeforeEach
    void setUp() throws Exception {
        // inject a simple holidayList into the controller using reflection
        Field f = HolidayController.class.getDeclaredField("holidayList");
        f.setAccessible(true);
        f.set(controller, List.of("Mandatory holiday for Deepavali 01-Jan-2025"));
    }

    @Test
    void getSpecificHolidays_returnsMap_whenServiceReturnsMap() throws Exception {
        Map.Entry<Month, List<String>> entry = new AbstractMap.SimpleEntry<>(Month.JANUARY, List.of("msg"));
        Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> map = Map.of(HolidayEnum.MANDATORY_HOLIDAYS, List.of(entry));
        when(holidayService.holidays(any(), any(), any(Integer.class))).thenReturn(map);

        @SuppressWarnings("unchecked")
        Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> result = (Map<HolidayEnum, List<Map.Entry<Month, List<String>>>>) controller.getSpecificHolidays("ALL", 0);
        assertThat(result).isNotNull();
        assertThat(result.keySet()).contains(HolidayEnum.MANDATORY_HOLIDAYS);
    }

    @Test
    void findHoliday_returnsLine_whenFound() throws Exception {
        String res = controller.findHoliday("deepavali");
        assertThat(res).containsIgnoringCase("Deepavali");
    }

    @Test
    void findHoliday_throwsRuntime_whenNotFound() {
        // inject an empty list
        try {
            Field f = HolidayController.class.getDeclaredField("holidayList");
            f.setAccessible(true);
            f.set(controller, List.of());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertThrows(RuntimeException.class, () -> controller.findHoliday("doesnotexist"));
    }
}
