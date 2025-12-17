package com.accenture.holiday.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayConfigurationTest {

    @Test
    void holidayList_returnsLines_whenResourcePresent() throws IOException {
        HolidayConfiguration cfg = new HolidayConfiguration();
        List<String> lines = cfg.holidayList();
        // The test resource `bangalore_holidays.log` (in src/test/resources) contains 3 sample lines
        assertThat(lines).isNotNull();
        assertThat(lines).isNotEmpty();
        assertThat(lines).anyMatch(s -> s.contains("holiday for"));
    }
}
