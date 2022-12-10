package social_network.repository.database;

public enum FriendshipField  {
    ID, USER_1, USER_2,FR_FROM,STATUS;

    public String getSqlValue() {
        return switch (this) {
            case ID -> "id";
            case USER_1 -> "user_1";
            case USER_2 -> "user_2";
            case FR_FROM -> "data_fr";
            case STATUS ->"status";
        };
    }
}
