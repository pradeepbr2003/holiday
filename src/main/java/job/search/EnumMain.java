package job.search;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public enum EnumMain {
    HOLIDAYS_LOG("holidays.log"),
    MANDATORY_HOLIDAYS("Mandatory Holidays"),
    FLOATING_HOLIDAYS("Floating Holidays"),
    MANDATORY_HOLIDAY("Mandatory holiday"),
    DATE_REG_EX("\\d+-\\w{3}-\\d{4}"),
    DATE_PATTERN_STR("dd-MMM-yyyy"),
    HOLIDAY_REASON_REG_EX("(for){1}\\D+"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday"),
    LOCATION_REG_EX("\\s(in){1}\\s\\w+");

    private String value;
    private Pattern pattern;
    private SimpleDateFormat sdf;

    EnumMain(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public Pattern pattern() {
        if (this.pattern == null) {
            this.pattern = Pattern.compile(value());
        }
        return this.pattern;
    }

    public SimpleDateFormat sdf() {
        if (this.sdf == null) {
            this.sdf = new SimpleDateFormat(value());
        }
        return this.sdf;
    }
}
