package repository.database;

import domain.Friendship;
import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static repository.database.FriendshipField.*;

public class DBFriendshipRepo extends DBBaseRepo<Long, Friendship> {
    public DBFriendshipRepo(String url, String username, String password) {
        super(url, username, password);
    }
    @Override
    public Friendship findOne(Long id) {
        String sql = "SELECT * FROM friendship WHERE id=?";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Friendship(
                        resultSet.getLong(ID.getSqlValue()),
                        resultSet.getLong(USER_1.getSqlValue()),
                        resultSet.getLong(USER_2.getSqlValue()),
                        resultSet.getTimestamp(FR_FROM.getSqlValue()).toLocalDateTime()
                );
            } else {
                return null;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Friendship> findAll() {
        String sql = "SELECT * FROM friendship";
        ArrayList<Friendship> friendships = new ArrayList<>();

        try {
            PreparedStatement statement = getStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Friendship friendship = new Friendship(
                        resultSet.getLong(ID.getSqlValue()),
                        resultSet.getLong(USER_1.getSqlValue()),
                        resultSet.getLong(USER_2.getSqlValue()),
                        resultSet.getTimestamp(FR_FROM.getSqlValue()).toLocalDateTime()
                );
                friendships.add(friendship);
            }
            return friendships;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship save(Friendship friendship) throws ValidationException {
        if (friendship.getIdUser1().equals(friendship.getIdUser2()))
            throw new ValidationException("Nu puteti adauga relatie intre aceeasi useri");


        String sql = "INSERT INTO friendship(id,user_1, user_2,data_fr) VALUES(?, ?, ?,?)";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1,friendship.getId());
            statement.setLong(2, friendship.getIdUser1());
            statement.setLong(3, friendship.getIdUser2());
            statement.setTimestamp(4, Timestamp.valueOf(friendship.getFriendsfrom()));
            statement.executeUpdate();
            return friendship;
        } catch (SQLException ignored) {}

        return null;
    }

    @Override
    public Long delete(Long id) {
        if (findOne(id) == null)
            throw new ExistingException("Prietenia cu id-ul dat nu exista");
        String sql = "DELETE FROM friendship WHERE id=?";
        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            return id;
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship update(Friendship entity) {
        return null;
    }


}
