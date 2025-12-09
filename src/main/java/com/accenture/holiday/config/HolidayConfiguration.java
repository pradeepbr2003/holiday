package com.accenture.holiday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.List;

import static com.accenture.holiday.util.HolidayEnum.BANGALORE_HOLIDAYS_LOG;

@Configuration
public class HolidayConfiguration {

    @Bean
    public List<String> holidayList() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(BANGALORE_HOLIDAYS_LOG.value())) {
            if (is == null) {
                throw new FileNotFoundException("Accenture Holidays File not found in classpath");
            }
            List<String> holidayList = new BufferedReader(new InputStreamReader(is))
                    .lines()
                    .toList();
            return holidayList;
        }
    }
}
