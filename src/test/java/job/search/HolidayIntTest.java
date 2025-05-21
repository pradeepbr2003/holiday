package job.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static job.search.helper.GeneralEnum.HOLIDAYS_LOG;

@RunWith(MockitoJUnitRunner.class)
public class HolidayIntTest {

    private List<String> contents = null;
    private Map<Date, Set<String>> holidayMap = null;

    @Before
    public void setUp() throws Exception {
        contents = Files.readAllLines(Paths.get(HOLIDAYS_LOG.value()));
        holidayMap = new HashMap<>();
    }

    @Test
    public void extractHoliday() {
        contents.forEach(content -> {
            HolidayInt.extractHoliday(content, holidayMap);
        });
    }
}