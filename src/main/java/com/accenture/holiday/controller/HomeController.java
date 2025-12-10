package com.accenture.holiday.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/home"})
public class HomeController {
    @GetMapping
    public String home() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<a href='/holiday?htype=mandatory'>Mandatory Holidays</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=mandatory&month=12'>Mandatory Holiday - Month</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=floating'>Floating Holidays</a>");
        buffer.append("<br>").append("<a href='/holiday?htype=floating&month=12'>Floating Holiday - Month</a>");
        buffer.append("<br>").append("<a href='/holiday/find/deepavali'>Find By Holiday Name (For Ex:- Deepavali)</a>");
        return buffer.toString();
    }
}
