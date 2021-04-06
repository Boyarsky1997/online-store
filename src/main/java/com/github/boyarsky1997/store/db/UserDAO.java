package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Admin;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Role;
import com.github.boyarsky1997.store.model.User;
import com.github.boyarsky1997.store.util.Resources;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final Logger logger = Logger.getLogger(UserDAO.class);

    private final Connection connection;

    public UserDAO() {
        this(ConnectionSingleton.getConnection());
    }

    UserDAO(Connection connection) {
        this.connection = connection;
    }
    public boolean checkExistLogin(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/checkClient.sql"));
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(String.format("The client is being searched by login %s ", login));
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage(), e);
        }
        return false;
    }

    public void insertUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/insertUser.sql"));
            preparedStatement.setString(1, user.getRole().toString());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getSurname());
            preparedStatement.setString(4, user.getLogin());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.execute();
            logger.info("Клієнт був успішно доданий");
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
    public User get(String login, String password) {
        User client;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/getUserByLoginAndPassword.sql"));
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            logger.info(("Searching for client by login and password "));

            if (resultSet.next()) {

                String string = resultSet.getString(2);
                if (string.equals(Role.BUYER.toString())) {
                    client = new Buyer();
                    client.setRole(Role.BUYER);
                } else {
                    client = new Admin();
                    client.setRole(Role.ADMIN);
                }

                client.setId(resultSet.getInt(1));

                client.setName(resultSet.getString(3));
                client.setSurname(resultSet.getString(4));
                client.setLogin(resultSet.getString(5));
                client.setPassword(resultSet.getString(6));

                return client;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
