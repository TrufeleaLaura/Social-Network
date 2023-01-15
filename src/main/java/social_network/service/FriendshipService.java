package social_network.service;


import social_network.Validators.ValidatorType;
import social_network.domain.Friendship;
import social_network.domain.User;
import social_network.exceptions.ExistingException;
import social_network.exceptions.ValidationException;
import social_network.repository.Repository;
import social_network.service.BaseService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;

public class FriendshipService extends BaseService<Long, Friendship> {
    private final Repository<Long, Friendship> friendshipRepo;
    private final Repository<Long, User> userRepo;

    public FriendshipService(Repository<Long, Friendship> repository, Repository<Long, User> userRepository) {
        super(ValidatorType.FRIENDSHIP, repository);
        System.out.println(repository.getClass());
        friendshipRepo = repository;
        this.userRepo = userRepository;
    }



}
