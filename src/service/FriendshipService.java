package service;

import Validators.ValidatorType;
import domain.Friendship;
import domain.Tuple;
import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;
import graph.Components;
import graph.Graph;
import repository.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class FriendshipService extends BaseService<Long, Friendship>{
    private final Repository<Long, Friendship> friendshipRepo;
    private final Repository<Long, User> userRepo;

    public FriendshipService(Repository<Long, Friendship> repository, Repository<Long, User> userRepository) {
        super(ValidatorType.FRIENDSHIP, repository);
        System.out.println(repository.getClass());
        friendshipRepo = repository;
        this.userRepo = userRepository;
    }

    /**
     * Returns a map with all users and their friends - used for printing the friendships
     *
     * @return a map where with keys as ids of users and values as their friends
     */
    public Map<User, List<User>> getAllUsersFriends() {
        HashMap<User, List<User>> map = new HashMap<>();
        List<Friendship> all = getAll();
        all.forEach(friendship -> {
            User user1 = userRepo.findOne(friendship.getIdUser1());
            User user2 = userRepo.findOne(friendship.getIdUser2());

            if (user1 != null)
                map.computeIfAbsent(user1, k -> new ArrayList<>());
            if (user2 != null)
                map.computeIfAbsent(user2, k -> new ArrayList<>());

            if (user1 != null && user2 != null) {
                map.get(user1).add(userRepo.findOne(friendship.getIdUser2()));
                map.get(user2).add(userRepo.findOne(friendship.getIdUser1()));

            }
        });
        return map;
    }

    public Map<User, HashMap<User,LocalDateTime>> getFriends() {
        HashMap<User, HashMap<User,LocalDateTime>> map = new HashMap<>();
        List<Friendship> all = getAll();
        all.forEach(friendship -> {
            User user1 = userRepo.findOne(friendship.getIdUser1());
            User user2 = userRepo.findOne(friendship.getIdUser2());

            if (user1 != null)
                map.computeIfAbsent(user1, k -> new HashMap<>());
            if (user2 != null)
                map.computeIfAbsent(user2, k -> new HashMap<>());

            if (user1 != null && user2 != null) {
                map.get(user1).computeIfAbsent(user2,k->friendship.getFriendsfrom());
                map.get(user2).computeIfAbsent(user1,k->friendship.getFriendsfrom());

            }
        });
        return map;
    }

    /**
     * Finds the relation between two given user ids
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     * @return the friendship between them
     */

    public Friendship getRelationByUsers(Long id1, Long id2) {
        return repository.findAll().stream()
                .filter(friendship -> friendship.getIdUser1().equals(id1) && friendship.getIdUser2().equals(id2))
                .findFirst()
                .orElse(null);
    }

    public List<Long> getAllUserIds() {
        List<Long> ids = new ArrayList<>();
        for (User user : userRepo.findAll())
            ids.add(user.getId());
        return ids;
    }

    /**
     * Creates a new Friendship between two users and adds it to the repo
     *
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     * @throws ValidationException if the relation is invalid
     */
    public void addFriend(String id, String id1, String id2,String since) throws ValidationException {

        Friendship friendShip = getFriendShip(id, id1, id2,since);
        if (getRelationByUsers(friendShip.getIdUser1(), friendShip.getIdUser2()) != null)
            throw new ExistingException("Exista deja o relatie intre acesti useri");
        if (userRepo.findOne(friendShip.getIdUser1()) == null)
            throw new ExistingException("User-ul 1 nu exista");
        if (userRepo.findOne(friendShip.getIdUser2()) == null)
            throw new ExistingException("User-ul 2 nu exista");
        super.addEntity(friendShip);
    }

    /**
     * Computes all the communities of users
     * @return a list of users' ids
     */
    public List<List<Long>> ConnectedComponents() {
        Graph<Long> graph = getFriendshipToGraph(repository.findAll());
        Components<Long> graphUtils = new Components<>(graph);
        for (Long userId : getAllUserIds()) graph.addNode(userId);
        return graphUtils.ConnectedComponents();
    }

    /**
     * creates a new instance of Friendship
     * @param id the id of the Friendship created
     * @param id1 the id of the first user
     * @param id2 the id of the second user
     * @return the created Friendship
     * @throws ValidationException if the ids are not of type Long (couldn't be converted to Long)
     */
    public Friendship getFriendShip(String id, String id1, String id2, String frFrom) throws ValidationException {
        try {
            Long userId1 = Long.parseLong(id1);
            Long userId2 = Long.parseLong(id2);
            Long friendshipId = Long.parseLong(id);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd[ HH:mm:ss]")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter();
            LocalDateTime since=LocalDateTime.parse(frFrom,formatter);
            return new Friendship(friendshipId, userId1, userId2,since);
        } catch (NumberFormatException ex) {
            throw new ValidationException("invalid friendship");
        }
    }
    /**
     * Maps a friendship to a graph
     * @param friendships a list of friendships
     * @return graph with the relations between users
     */
    public Graph<Long> getFriendshipToGraph(List<Friendship> friendships) {
        List<Tuple<Long, Long>> nodesList = new ArrayList<>();
        for (Friendship friendship : friendships) {
            nodesList.add(new Tuple<>(friendship.getIdUser1(), friendship.getIdUser2()));
        }
        return new Graph<>(nodesList);
    }






}
