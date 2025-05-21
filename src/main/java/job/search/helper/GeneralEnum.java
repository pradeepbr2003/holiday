package job.search.helper;

public enum GeneralEnum {
    EMPTY_STR(""),
    HOLIDAYS_LOG("holidays.log"),
    MANDATORY_HOLIDAY("Mandatory holiday"),
    FLOATING_HOLIDAY("Floating holiday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private String value;

    GeneralEnum(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
