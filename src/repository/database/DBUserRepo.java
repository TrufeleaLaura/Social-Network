package repository.database;

import domain.User;
import exceptions.ExistingException;
import exceptions.ValidationException;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.database.UserField.*;

public class DBUserRepo implements Repository<Long, User> {
    private final String url;
    private final String username;
    private final String password;

    public DBUserRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private PreparedStatement getStatement(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection.prepareStatement(sql);
    }


    @Override
    public User findOne(Long id) {
        String sql = "SELECT * FROM users WHERE id=?";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getLong(ID.getSqlValue()),
                        resultSet.getString(NUME.getSqlValue()),
                        resultSet.getString(EMAIL.getSqlValue()));
            } else  {
                return null;
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try {
            PreparedStatement statement = getStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getLong(ID.getSqlValue()),
                        resultSet.getString(NUME.getSqlValue()),
                        resultSet.getString(EMAIL.getSqlValue())
                );
                users.add(user);
            }

            return users;
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
        return null;
    }

    @Override
    public User save(User entity) throws ValidationException {
        String sql = "INSERT INTO users(id,nume, email) VALUES(?,?, ?)";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setString(3, entity.getEmail());
            statement.executeUpdate();
            return entity;
        } catch (SQLException throwables) {
            //System.out.println(throwables.getMessage());
        }
        return null;
    }


    @Override
    public Long delete(Long id) {
        if (findOne(id) == null)
            throw new ExistingException("User-ul cu id-ul dat nu exista");

        String sql = "DELETE FROM users WHERE id=?";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User user) {

        String sql = "UPDATE users SET email=? WHERE id=?";

        try {
            PreparedStatement statement = getStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
            return user;
        } catch (SQLException ignored) {
        }
        return null;
    }
}
