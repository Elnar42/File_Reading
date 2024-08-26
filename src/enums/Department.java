package enums;

public enum Department {
    HR,
    IT,
    SALES,
    MARKETING,
    FINANCE,
    UNKNOWN;


    public static Department getDepartment(String line) {
        try {
            return Department.valueOf(line);
        } catch (IllegalArgumentException e) {
            return Department.UNKNOWN;
        }
    }
}
