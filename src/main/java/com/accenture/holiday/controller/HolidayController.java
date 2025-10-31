package com.accenture.holiday.controller;

import com.accenture.holiday.service.HolidayService;
import com.accenture.holiday.util.HolidayEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static com.accenture.holiday.util.HolidayEnum.BANGALORE_HOLIDAYS_LOG;

@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public Map getSpecificHolidays(@RequestParam(required = false, defaultValue = "ALL") String htype,
                                   @RequestParam(required = false, defaultValue = "0") Integer month) throws IOException {
        List<String> holidayList = Files.readAllLines(Paths.get(BANGALORE_HOLIDAYS_LOG.value()));
        return holidayService.holidays(holidayList, htype, month);
    }

    @GetMapping("/find/{leaveName}")
    public Map<Month, String> findHoliday(@PathVariable String leaveName) throws Exception {
        List<String> holidayList = Files.readAllLines(Paths.get(BANGALORE_HOLIDAYS_LOG.value()));
        Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> holidays = holidayService.holidays(holidayList, "ALL", 0);
        return holidays.values().stream().flatMap(list -> list.stream())
                .flatMap(e -> e.getValue().stream().filter(str -> str.toUpperCase().contains(leaveName.toUpperCase()))
                        .map(s -> Map.of(e.getKey(), s))).findAny().orElseThrow(() -> new RuntimeException("Leave not found"));
    }

}
