package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Product;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDAOTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private ProductDAO productDAO;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        productDAO = new ProductDAO(mockConnection);
    }

    @Test
    public void testAddProduct() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        productDAO.addProduct("Milk", 2.2, 5, "milk");

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setString(1, "Milk");
        Mockito.verify(mockPreparedStatement).setDouble(2, 2.2);
        Mockito.verify(mockPreparedStatement).setInt(3, 5);
        Mockito.verify(mockPreparedStatement).setString(4, "milk");
    }

    @Test
    public void testAddProductWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        productDAO.addProduct("Milk", 2.2, 5, "milk");

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setString(1, "Milk");
        Mockito.verify(mockPreparedStatement).setDouble(2, 2.2);
        Mockito.verify(mockPreparedStatement).setInt(3, 5);
        Mockito.verify(mockPreparedStatement).setString(4, "milk");
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Product product = new Product();
        product.setId(2);
        product.setName("Milk");
        product.setPrice(2.2);
        product.setCount(5);
        product.setDescription("milk");

        productDAO.updateProduct(product);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, product.getId());
        Mockito.verify(mockPreparedStatement).setString(2, product.getName());
        Mockito.verify(mockPreparedStatement).setDouble(3, product.getPrice());
        Mockito.verify(mockPreparedStatement).setInt(4, product.getCount());
        Mockito.verify(mockPreparedStatement).setString(5, product.getDescription());
        Mockito.verify(mockPreparedStatement).setInt(6, product.getId());
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).execute();
    }

    @Test
    public void testUpdateProductWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);
        Product product = new Product();
        product.setId(2);
        product.setName("Milk");
        product.setPrice(2.2);
        product.setCount(5);
        product.setDescription("milk");

        productDAO.updateProduct(product);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, product.getId());
        Mockito.verify(mockPreparedStatement).setString(2, product.getName());
        Mockito.verify(mockPreparedStatement).setDouble(3, product.getPrice());
        Mockito.verify(mockPreparedStatement).setInt(4, product.getCount());
        Mockito.verify(mockPreparedStatement).setString(5, product.getDescription());
        Mockito.verify(mockPreparedStatement).setInt(6, product.getId());
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).execute();
    }

    @Test
    public void testGetAllProductOnOrderId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(2, 1);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn("RO", "MA");
        Mockito.when(mockResultSet.getDouble(3))
                .thenReturn(2.2, 3.3);
        Mockito.when(mockResultSet.getInt(4))
                .thenReturn(6, 7);
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("ra", "ka");

        List<Product> actual = productDAO.getAllProductOnOrderId(2);

        Assert.assertEquals(actual.size(), 2);
        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.get(0).getId(), 2);
        Assert.assertEquals(actual.get(0).getName(), "RO");
        Assert.assertEquals(actual.get(0).getPrice(), 2.2);
        Assert.assertEquals(actual.get(0).getCount(), 6);
        Assert.assertEquals(actual.get(0).getDescription(), "ra");
        Assert.assertEquals(actual.get(1).getId(), 1);
        Assert.assertEquals(actual.get(1).getName(), "MA");
        Assert.assertEquals(actual.get(1).getPrice(), 3.3);
        Assert.assertEquals(actual.get(1).getCount(), 7);
        Assert.assertEquals(actual.get(1).getDescription(), "ka");
    }

    @Test
    public void testGetAllProductOnOrderIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);
        List<Product> actual = productDAO.getAllProductOnOrderId(2);

        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
    }

    @Test
    public void testGetById() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(2);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn("Roman");
        Mockito.when(mockResultSet.getDouble(3))
                .thenReturn(2.2);
        Mockito.when(mockResultSet.getInt(4))
                .thenReturn(5);
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("SA");

        Product actual = productDAO.getById(22);

        Assert.assertEquals(actual.getId(), 2);
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getPrice(), 2.2);
        Assert.assertEquals(actual.getCount(), 5);
        Assert.assertEquals(actual.getDescription(), "SA");
        Mockito.verify(mockPreparedStatement).setInt(1, 22);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetByIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        Product actual = productDAO.getById(22);

        Mockito.verify(mockPreparedStatement).setInt(1, 22);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetAll() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(44, 55);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn("RO", "NA");
        Mockito.when(mockResultSet.getDouble(3))
                .thenReturn(5.5, 3.4);
        Mockito.when(mockResultSet.getInt(4))
                .thenReturn(2, 3);
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("dd", "ff");

        List<Product> all = productDAO.getAll();

        Assert.assertEquals(all.size(), 2);
        Assert.assertNotNull(all);
        Assert.assertFalse(all.isEmpty());
        Assert.assertEquals(all.get(0).getId(),44);
        Assert.assertEquals(all.get(0).getName(),"RO");
        Assert.assertEquals(all.get(0).getPrice(),5.5);
        Assert.assertEquals(all.get(0).getCount(),2);
        Assert.assertEquals(all.get(0).getDescription(),"dd");
        Assert.assertEquals(all.get(1).getId(),55);
        Assert.assertEquals(all.get(1).getName(),"NA");
        Assert.assertEquals(all.get(1).getPrice(),3.4);
        Assert.assertEquals(all.get(1).getCount(),3);
        Assert.assertEquals(all.get(1).getDescription(),"ff");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetAllWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Product> all = productDAO.getAll();

        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }
}
