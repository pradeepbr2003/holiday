package com.accenture.holiday;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HolidayApplicationTest {

    @Test
    void contextLoads() {
        // This test will pass if the application context loads successfully
    }

    @Test
    void mainRunsWithoutException_whenArgsEmpty() {
        Assertions.assertDoesNotThrow(() -> HolidayApplication.main(new String[]{}));
    }
}
