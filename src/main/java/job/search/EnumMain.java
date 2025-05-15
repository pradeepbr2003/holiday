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
    SUNDAY("Sunday");

    private String value;
    private Pattern datePattern;
    private Pattern reasonPattern;
    private SimpleDateFormat sdf;

    EnumMain(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    public Pattern datePattern() {
        if (this.datePattern == null) {
            datePattern = Pattern.compile(value());
        }
        return datePattern;
    }

    public Pattern reasonPattern() {
        if (this.reasonPattern == null) {
            this.reasonPattern = Pattern.compile(value());
        }
        return this.reasonPattern;
    }

    public SimpleDateFormat sdf() {
        if (this.sdf == null) {
            this.sdf = new SimpleDateFormat(value());
        }
        return this.sdf;
    }
}
