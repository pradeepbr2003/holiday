package com.accenture.holiday.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple controller that exposes a small HTML fragment with links to common
 * holiday API usages (used for quick manual testing via a browser).
 */
@RestController
@RequestMapping({"/home"})
public class HomeController {
    /**
     * Returns a small HTML snippet with links to example API queries.
     *
     * @return an HTML string containing links to example holiday queries
     */
    @GetMapping
    public String home() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<a href='/holiday?htype=mandatory'>Mandatory Holidays</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=mandatory&month=12'>Mandatory Holiday - Month</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=floating'>Floating Holidays</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=floating&month=12'>Floating Holiday - Month</a>");
        buffer.append("<br>").append("<a href='/holiday/find/deepavali'>Find By Holiday Name (For Ex:- Deepavali)</a>");
        return buffer.toString();
    }
}
