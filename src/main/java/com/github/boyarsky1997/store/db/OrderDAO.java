package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.util.Resources;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final Logger logger = Logger.getLogger(OrderDAO.class);

    private final Connection connection;

    public OrderDAO() {
        this(ConnectionSingleton.getConnection());
    }

    OrderDAO(Connection connection) {
        this.connection = connection;
    }


    public List<Order> getAllOrderInBuyerId(int buyerId) {
        List<Order> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/getAllOrderOnBuyerId.sql"));
            preparedStatement.setInt(1, buyerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setId(resultSet.getInt(1));
                order.setBuyerId(resultSet.getInt(2));
                order.setPrice(resultSet.getDouble(3));
                order.setDate(resultSet.getDate(4));
                result.add(order);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;

    }

    public int addProductInOrder(int buyerId, double price, Date date) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/insertProductInOrderByBuyerIdAndProductId.sql"));
            preparedStatement.setInt(1, buyerId);
            preparedStatement.setDouble(2, price);
            preparedStatement.setDate(3, date);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement(Resources.load("/sql/getLastId.sql"));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    public void addProductInOrderByOrderIdAndProductId(int productId, int orderId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/addProductInOrderByOrderIdAndProductId.sql"));
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}