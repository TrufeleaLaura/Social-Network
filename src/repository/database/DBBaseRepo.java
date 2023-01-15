package repository.database;

import domain.Entity;
import repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DBBaseRepo<ID,E extends Entity<ID>> implements Repository<ID,E> {
    private final String url;
    private final String username;
    private final String password;

    public DBBaseRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    protected PreparedStatement getStatement(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection.prepareStatement(sql);
    }
}
