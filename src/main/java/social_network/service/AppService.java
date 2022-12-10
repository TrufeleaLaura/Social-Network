package social_network.service;

import social_network.Observer.Observable;
import social_network.Observer.Observer;
import social_network.Validators.Validator;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.repository.database.DBFriendshipRepo;
import social_network.repository.database.DBUserRepo;

import java.util.*;

public class AppService implements Service, Observable {
    private DBUserRepo repository_user;
    private final Validator<User> validator;
    private final Validator<Friendship> validator_fr;
    private DBFriendshipRepo repository_friendship;



    public AppService(DBUserRepo repository_user, DBFriendshipRepo repository_friendship, Validator<User> validator, Validator<Friendship> validator2) {
        this.repository_user = repository_user;
        this.validator = validator;
        this.validator_fr = validator2;
        this.repository_friendship = repository_friendship;
        observers = new ArrayList<>();
    }

    List<Observer> observers;
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserevers() {
        observers.stream().forEach(observer -> observer.update());
    }
    public List<User> getAllUsers() {
        return repository_user.findAll();
    }
    public User getUserByEmail(String email) {
        return repository_user.findOne_email(email);
    }



    @Override
    public void addFriendship(Long id1, Long id2)  {
        Friendship f1 = new Friendship((id1+id2),id1, id2,"PENDING");
        validator_fr.validate(f1);
        Friendship friendship = existPendingFriendship(f1);
        if (friendship == null) {
            repository_friendship.save(f1);
        } else repository_friendship.acceptFriendshipInDB(friendship);
        notifyObserevers();

    }

    public Friendship existPendingFriendship(Friendship f1) {
        List<Friendship> friendships = repository_friendship.findAll();
        Optional<Friendship> optionalFriendship = friendships.stream()
                .filter(friendship -> Objects.equals(friendship.getIdUser1(), f1.getIdUser2()) && Objects.equals(friendship.getIdUser2(), f1.getIdUser1()))
                .findFirst();

        boolean isPresent = optionalFriendship.isPresent();
        if (isPresent)
            return optionalFriendship.get();
        else return null;
    }

    @Override
    public HashMap<User, String> getFriends(Long id) {

        HashMap<User, String> users = new HashMap<>();
        List<Friendship> friendships = repository_friendship.findAll();
        for (Friendship friendship : friendships)
            if (friendship.getStatus().equals("ACCEPTED")) {
                if (Objects.equals(friendship.getIdUser1(), id))
                    users.put(repository_user.findOne(friendship.getIdUser2()), friendship.getFriendsFrom());
                else if (Objects.equals(friendship.getIdUser2(), id))
                    users.put(repository_user.findOne(friendship.getIdUser1()), friendship.getFriendsFrom());
            }

        return users;
    }
    @Override
    public List<User> getFriendRequests(Long id) {
        List<User> friendRequests = new ArrayList<>();
        for (Friendship friendship : repository_friendship.findAll())
            if (friendship.getStatus().equals("PENDING")) {
                if (friendship.getIdUser2() == id)
                    friendRequests.add(repository_user.findOne(friendship.getIdUser1()));
            }
        return friendRequests;
    }
}
