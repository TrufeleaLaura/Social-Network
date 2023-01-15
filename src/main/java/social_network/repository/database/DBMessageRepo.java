package social_network.repository.database;

import social_network.domain.Friendship;
import social_network.domain.Message;
import social_network.exceptions.ExistingException;
import social_network.exceptions.ValidationException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import static social_network.repository.database.FriendshipField.FR_FROM;
import static social_network.repository.database.MessageField.*;

public class DBMessageRepo  {
    private final String url;
    private final String username;
    private final String password;

    public DBMessageRepo(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    protected PreparedStatement getStatement(String sql) throws SQLException {
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection.prepareStatement(sql);
    }



        public int size(){
        String sql = "SELECT COUNT(*) FROM messages";
            try {
                PreparedStatement statement = getStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return 0;
        }

        public List<Message> findAllFor(String name) {
            String sql = "SELECT * FROM messages where from_user=? or to_user=?";
            ArrayList<Message> messages = new ArrayList<>();

            try {
                PreparedStatement statement = getStatement(sql);
                statement.setString(1, name);
                statement.setString(2, name);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    Message message = new Message(
                            resultSet.getInt(ID.getSqlValue()),
                            resultSet.getString(MESSAGE.getSqlValue()),
                            resultSet.getString(FROM.getSqlValue()),
                            resultSet.getString(TO.getSqlValue()),
                            resultSet.getTimestamp(TIME.getSqlValue()).toLocalDateTime()

                    );
                    messages.add(message);
                }
                return messages;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }


        public Message save(Message message)  {
            String sql = "INSERT INTO messages (id, mesaj, from_user, to_user, data) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement statement = getStatement(sql);
                statement.setInt(1,message.getId());
                statement.setString(2, message.getMessage());
                statement.setString(3, message.getFrom());
                statement.setString(4, message.getTo());
                statement.setTimestamp(5, Timestamp.valueOf(message.getTime()));
                statement.executeUpdate();
                return message;
            } catch (SQLException ignored) {
                ignored.printStackTrace();
            }

            return null;
        }

        public String delete(String name1,String name2) {
            String sql = "DELETE FROM messages WHERE (from_user=? and to_user=?) or (from_user=? and to_user=?)";
            try {
                PreparedStatement statement = getStatement(sql);
                statement.setString(1, name1);
                statement.setString(2, name2);
                statement.setString(3, name2);
                statement.setString(4, name1);
                statement.executeUpdate();
                return name1+" "+name2;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        }


}
