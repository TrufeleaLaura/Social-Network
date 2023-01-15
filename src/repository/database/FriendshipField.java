package repository.database;

public enum FriendshipField  {
    ID, USER_1, USER_2,FR_FROM;

    public String getSqlValue() {
        return switch (this) {
            case ID -> "id";
            case USER_1 -> "user_1";
            case USER_2 -> "user_2";
            case FR_FROM -> "data_fr";
        };
    }
}
