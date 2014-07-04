package jWufoo;

public class Filter {

    String getFilterAs(int i) {
            return String.format("&Filter%s=%s+%s+%s", i, matchTarget, operator, getValue());
    }

    public enum OPERATOR {
        Contains,
        Does_not_contain,
        Begins_with,
        Ends_with,
        Is_less_than,
        Is_greater_than,
        Is_on,
        Is_before,
        Is_after,
        Is_not_equal_to,
        Is_equal_to,
        Is_not_NULL
    }
    
    String matchTarget;
    OPERATOR operator;
    String value;


    @SuppressWarnings("deprecation")
    private String getValue() {
        return java.net.URLEncoder.encode(this.value);
    }

    public Filter(String matchTarget, OPERATOR operator, String value) {
        this.matchTarget = matchTarget;
        this.operator = operator;
        this.value = value;
    }
    
    public Filter(Field field, OPERATOR operator, String value) {
        this(field.id, operator, value);
    }
}
