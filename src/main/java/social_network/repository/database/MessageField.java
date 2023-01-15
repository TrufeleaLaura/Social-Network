package social_network.repository.database;

public enum MessageField {
    ID, MESSAGE, FROM,TO,TIME;

    public String getSqlValue() {
        return switch (this) {
            case ID -> "id";
            case MESSAGE -> "mesaj";
            case FROM -> "from_user";
            case TIME -> "data";
            case TO ->"to_user";
        };
    }
}
