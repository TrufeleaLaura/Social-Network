package social_network.service;

import social_network.domain.User;

import java.util.HashMap;
import java.util.List;

public interface Service {

    void addFriendship(Long id1, Long id2);



    HashMap<User, String> getFriends(Long id);

    List<User> getFriendRequests(Long id);
}
