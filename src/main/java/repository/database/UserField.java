package repository.database;

public enum UserField {
    ID, NUME, EMAIL;

    public String getSqlValue() {
        return switch (this) {
            case ID -> "id";
            case NUME -> "nume";
            case EMAIL -> "email";
        };
    }
}
