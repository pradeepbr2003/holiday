package com.accenture.holiday.controller;

import com.accenture.holiday.service.HolidayService;
import com.accenture.holiday.util.HolidayEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;

/**
 * REST controller that exposes endpoints to query Bangalore holidays.
 * <p>
 * Endpoints:
 * - GET /holiday : list holidays (supports filters)
 * - GET /holiday/find/{leaveName} : find the first holiday matching the provided name fragment
 */
@RestController
@RequestMapping("/holiday")
public class HolidayController {

    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HolidayService holidayService;

    @Autowired
    private List<String> holidayList;

    /**
     * Returns holidays grouped by type and month.
     *
     * @param htype optional filter for holiday type (Mandatory, Floating or ALL). Defaults to ALL.
     * @param month optional month filter (1-12). Defaults to 0 (no month filtering).
     * @return a map keyed by holiday type containing month -> list of holiday strings
     * @throws IOException when holiday data cannot be read (propagated from underlying services)
     */
    @GetMapping
    public Map getSpecificHolidays(@RequestParam(required = false, defaultValue = "ALL") String htype,
                                   @RequestParam(required = false, defaultValue = "0") Integer month) throws IOException {
        LOG.info("Holiday : {}", holidayList);
        return holidayService.holidays(holidayList, htype, month);
    }

    /**
     * Finds a holiday by a name fragment (case-insensitive) and returns the raw line
     * from the holidays list. If not found, a RuntimeException is thrown.
     *
     * @param leaveName substring to search for in holiday names
     * @return the raw holiday entry that matches the provided fragment
     * @throws Exception if no matching holiday is found
     */
    @GetMapping("/find/{leaveName}")
    public String findHoliday(@PathVariable String leaveName) throws Exception {
        return holidayList.stream()
                .filter(str -> str.toUpperCase().contains(leaveName.toUpperCase())).findAny()
                .orElseThrow(() -> new RuntimeException("Leave not found"));
    }

    //    @GetMapping("/find/{leaveName}")
    public Map<Month, String> findOutHoliday(@PathVariable String leaveName) throws Exception {
        LOG.info("Holiday : {}", holidayList);
        Map<HolidayEnum, List<Map.Entry<Month, List<String>>>> holidays = holidayService.holidays(holidayList, "ALL", 0);
        return holidays.values().stream().flatMap(list -> list.stream())
                .flatMap(e -> e.getValue().stream().filter(str -> str.toUpperCase().contains(leaveName.toUpperCase()))
                        .map(s -> Map.of(e.getKey(), s))).findAny().orElseThrow(() -> new RuntimeException("Leave not found"));
    }

}
