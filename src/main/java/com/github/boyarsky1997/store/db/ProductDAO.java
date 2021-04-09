package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Product;
import com.github.boyarsky1997.store.util.Resources;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final Logger logger = Logger.getLogger(ProductDAO.class);

    private final Connection connection;

    public ProductDAO() {
        this(ConnectionSingleton.getConnection());
    }

    ProductDAO(Connection connection) {
        this.connection = connection;
    }


    public void addProduct(String name, double price, int count, String description) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/addProduct.sql"));
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, count);
            preparedStatement.setString(4, description);

            preparedStatement.execute();

        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void updateProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/updateProducts.sql"));
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setInt(4, product.getCount());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setInt(6, product.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }


    }
    public List<Product> getAllProductOnOrderId(int orderId) {
        List<Product> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/getAllProductOnOrderId.sql"));
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setCount(resultSet.getInt(4));
                product.setDescription(resultSet.getString(5));
                result.add(product);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
    public Product getById(int id) {
        Product product = new Product();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(Resources.load("/sql/getProductById.sql"));
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setCount(resultSet.getInt(4));
                product.setDescription(resultSet.getString(5));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return product;
    }

    public List<Product> getAll() {
        List<Product> result = new ArrayList<>();
        ;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    Resources.load("/sql/getAllProducts.sql"));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setName(resultSet.getString(2));
                product.setPrice(resultSet.getDouble(3));
                product.setCount(resultSet.getInt(4));
                product.setDescription(resultSet.getString(5));
                result.add(product);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
}
