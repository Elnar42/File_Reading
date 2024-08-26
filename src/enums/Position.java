package enums;

public enum Position {
    INTERN,
    JUNIOR,
    SENIOR,
    MANAGER,
    DIRECTOR,
    EXECUTIVE,
    UNKNOWN;

    public static Position getPosition(String line) {
        try {
            return Position.valueOf(line);
        } catch (IllegalArgumentException e) {
            return Position.UNKNOWN;
        }
    }

}
