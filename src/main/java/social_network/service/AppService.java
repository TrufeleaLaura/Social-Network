package social_network.service;

import social_network.Observer.Observable;
import social_network.Observer.Observer;
import social_network.Validators.Validator;
import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.domain.User;
import social_network.repository.database.DBFriendshipRepo;
import social_network.repository.database.DBMessageRepo;
import social_network.repository.database.DBUserRepo;
import social_network.repository.database.MessageField;

import java.util.*;

public class AppService implements Service, Observable {
    private DBUserRepo repository_user;
    private final Validator<User> validator;
    private final Validator<Friendship> validator_fr;
    private DBFriendshipRepo repository_friendship;
    private DBMessageRepo repository_message;
    private static int x;



    public AppService(DBUserRepo repository_user, DBFriendshipRepo repository_friendship, Validator<User> validator, Validator<Friendship> validator2, DBMessageRepo repository_message) {
        this.repository_user = repository_user;
        this.validator = validator;
        this.validator_fr = validator2;
        this.repository_friendship = repository_friendship;
        this.repository_message = repository_message;
        this.x=repository_message.size();
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
    public void notifyObservers() {
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
        Friendship f1= new Friendship((id1+id2),id1, id2,"PENDING");
        if (getRelationByUsers(id1,id2)==null) {
            repository_friendship.save(f1); notifyObservers();

        }
        else{ repository_friendship.acceptFriendshipInDB(f1);
            notifyObservers();
            }

    }

    public void addMessage(String msj,String nume1, String nume2)  {
        //int id=repository_user.findOne_nume(nume1).getId().intValue()+repository_user.findOne_nume(nume2).getId().intValue()+x;
        int id=x;
        x++;
        Message f1= new Message(id,msj, nume1,nume2);
        repository_message.save(f1);
        notifyObservers();

    }
    @Override
    public void removeFriendship(Long id1, Long id2) throws Exception {
        repository_friendship.delete(id1+id2);
        repository_message.delete(repository_user.findOne(id1).getName(),repository_user.findOne(id2).getName());
        notifyObservers();
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

    public List<User> getSentRequests(Long id) {
        List<User> friendRequests = new ArrayList<>();
        for (Friendship friendship : repository_friendship.findAll())
            if (friendship.getStatus().equals("PENDING")) {
                if (friendship.getIdUser1() == id)
                    friendRequests.add(repository_user.findOne(friendship.getIdUser2()));
            }
        return friendRequests;
    }
    public List<Message> getMessage(String numeuser,String numefriend) {
        List<Message> messages = new ArrayList<>();
        for (Message message : repository_message.findAllFor(numeuser))
            if (message.getFrom().equals(numeuser) && message.getTo().equals(numefriend) || message.getFrom().equals(numefriend) && message.getTo().equals(numeuser))
                messages.add(message);
        return messages;
    }

    @Override
    public Friendship getRelationByUsers(Long id1, Long id2) {
        return repository_friendship.findAll().stream()
                .filter(friendship -> friendship.getIdUser1().equals(id1) && friendship.getIdUser2().equals(id2) ||
                        friendship.getIdUser1().equals(id2) && friendship.getIdUser2().equals(id1))
                .findFirst()
                .orElse(null);
    }
    public User findUserByName(String name) {
        return repository_user.findOne_nume(name);
    }
}
