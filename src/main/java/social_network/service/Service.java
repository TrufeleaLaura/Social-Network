package social_network.service;

import social_network.domain.Friendship;
import social_network.domain.User;

import java.util.HashMap;
import java.util.List;

public interface Service {
    //service pentru  gui bazat pe prietenii

    void addFriendship(Long id1, Long id2);


    HashMap<User, String> getFriends(Long id);

    List<User> getFriendRequests(Long id);

    void removeFriendship(Long id1, Long id2) throws Exception;

    Friendship getRelationByUsers(Long id1, Long id2);
}
