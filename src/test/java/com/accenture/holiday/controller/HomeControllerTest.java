package com.accenture.holiday.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeControllerTest {

    @Test
    void home_returnsHtmlWithExpectedLinks() {
        HomeController controller = new HomeController();
        String html = controller.home();
        assertThat(html).contains("/holiday?htype=mandatory");
        assertThat(html).contains("/holiday/find/deepavali");
    }
}
