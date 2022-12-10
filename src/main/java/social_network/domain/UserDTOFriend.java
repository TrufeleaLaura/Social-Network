package social_network.domain;


public class UserDTOFriend {
    private String friendsSince;
    private String name_user;
    private long UID;

    public UserDTOFriend(User user,String friendsSince) {
        this.friendsSince = friendsSince;
        String name=user.getName();
        this.name_user = name;
        UID= user.getId();
    }

    public long getUID() {
        return UID;
    }

    public String getFriendsSince() {
        return friendsSince;
    }

    public String getName_user() {
        return name_user;
    }
}
