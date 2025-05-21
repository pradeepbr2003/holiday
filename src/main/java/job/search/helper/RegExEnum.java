package job.search.helper;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public enum RegExEnum {
    DATE_PATTERN("dd-MMM-yyyy"),
    DATE_REG_EX("\\d+-\\w{3}-\\d{4}"),
    HOLIDAY_REASON_REG_EX("(for){1}\\D+"),
    LOCATION_REG_EX("\\s(in){1}\\s\\w+");

    private String value;
    private Pattern pattern;
    private SimpleDateFormat sdf;

    RegExEnum(String value) {
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

    public SimpleDateFormat formatter() {
        if (this.sdf == null) {
            this.sdf = new SimpleDateFormat(value());
        }
        return this.sdf;
    }
}
