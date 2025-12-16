package com.accenture.holiday.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.List;

import static com.accenture.holiday.util.HolidayEnum.BANGALORE_HOLIDAYS_LOG;

/**
 * Spring configuration that exposes application-specific beans.
 */
@Configuration
public class HolidayConfiguration {

    /**
     * Loads the holiday lines from the classpath resource defined in {@link com.accenture.holiday.util.HolidayEnum#BANGALORE_HOLIDAYS_LOG}.
     *
     * @return a list of raw holiday lines
     * @throws IOException when the resource cannot be read or is missing
     */
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
