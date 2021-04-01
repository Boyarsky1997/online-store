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
