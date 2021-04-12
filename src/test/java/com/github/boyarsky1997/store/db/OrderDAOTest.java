package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.model.Status;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.List;

public class OrderDAOTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private OrderDAO orderDAO;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        orderDAO = new OrderDAO(mockConnection);
    }

    @Test
    public void testGetPaidProducts() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).
                thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).
                thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(1, 2);
        Mockito.when(mockResultSet.getInt(2)).thenReturn(3, 4);
        Mockito.when(mockResultSet.getDouble(3)).thenReturn(2.3, 3.2);
        Mockito.when(mockResultSet.getDate(4)).thenReturn(new Date(22), new Date(13));

        List<Order> actual = orderDAO.getPaidProducts(123);

        Assert.assertNotNull(actual);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertEquals(actual.size(), 2);
        Assert.assertEquals(actual.get(0).getId(), 1);
        Assert.assertEquals(actual.get(0).getBuyerId(), 3);
        Assert.assertEquals(actual.get(0).getPrice(), 2.3);
        Assert.assertEquals(actual.get(0).getDate(), new Date(22));
        Assert.assertEquals(actual.get(1).getId(), 2);
        Assert.assertEquals(actual.get(1).getBuyerId(), 4);
        Assert.assertEquals(actual.get(1).getPrice(), 3.2);
        Assert.assertEquals(actual.get(1).getDate(), new Date(13));
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 123);

    }

    @Test
    public void testGetPaidProductsWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);


        List<Order> paidProducts = orderDAO.getPaidProducts(2);


        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }


    @Test
    public void testGetAllOrderInBuyerId() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).
                thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).
                thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(2);
        Mockito.when(mockResultSet.getInt(2)).thenReturn(4);
        Mockito.when(mockResultSet.getDouble(3)).thenReturn(2.2);
        Mockito.when(mockResultSet.getDate(4)).thenReturn(new Date(22));

        Order actual = orderDAO.getAllOrderInBuyerId(2);

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), 2);
        Assert.assertEquals(actual.getBuyerId(), 4);
        Assert.assertEquals(actual.getPrice(), 2.2);
        Assert.assertEquals(actual.getDate(), new Date(22));

        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetAllOrderInBuyerIdWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).
                thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).
                thenThrow(SQLException.class);

        Order actual = orderDAO.getAllOrderInBuyerId(2);

        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetExistingOpenOrder() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).
                thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next()).thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1)).thenReturn(2);

        int actual = orderDAO.getExistingOpenOrder(3);


        Assert.assertEquals(actual, 2);

        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, 3);
    }

    @Test
    public void testGetExistingOpenOrderWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery()).
                thenThrow(SQLException.class);

        int actual = orderDAO.getExistingOpenOrder(3);


        Assert.assertEquals(actual, -1);

        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, 3);
    }

    @Test
    public void testCreateNewOrder() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true);
        Mockito.when(mockResultSet.getInt(Mockito.anyInt()))
                .thenReturn(123);

        int actual = orderDAO.createNewOrder(2, 2.2, new Date(22));

        Assert.assertEquals(actual, 123);
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).setDouble(2, 2.2);
        Mockito.verify(mockPreparedStatement).setDate(3, new Date(22));
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockConnection, Mockito.times(2)).prepareStatement(Mockito.anyString());
        Mockito.verify(mockResultSet).getInt(1);
    }

    @Test
    public void testCreateNewOrderWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        int actual = orderDAO.createNewOrder(2, 2.2, new Date(22));

        Assert.assertEquals(actual, 0);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testCreateNewOrderWhenException1() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        int actual = orderDAO.createNewOrder(2, 2.2, new Date(22));

        Assert.assertEquals(actual, 0);
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testAddProductInOrder() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        orderDAO.addProductInOrder(2, 3);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).setInt(2, 3);
    }

    @Test
    public void testAddProductInOrderWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        orderDAO.addProductInOrder(2, 3);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
        Mockito.verify(mockPreparedStatement).setInt(2, 3);
    }

    @Test
    public void testPayment() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);

        Order order = new Order();
        order.setId(2);
        order.setBuyerId(1);
        order.setPrice(2.2);
        order.setDate(new Date(22));
        order.setStatus(Status.PAID);

        orderDAO.payment(order);

        Mockito.verify(mockPreparedStatement).setInt(1, order.getId());
        Mockito.verify(mockPreparedStatement).setInt(2, order.getBuyerId());
        Mockito.verify(mockPreparedStatement).setDouble(3, order.getPrice());
        Mockito.verify(mockPreparedStatement).setDate(4, order.getDate());
        Mockito.verify(mockPreparedStatement).setString(5, order.getStatus().toString());
        Mockito.verify(mockPreparedStatement).setInt(6, order.getId());
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

    @Test
    public void testPaymentWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);

        Order order = new Order();
        order.setId(2);
        order.setBuyerId(1);
        order.setPrice(2.2);
        order.setDate(new Date(22));
        order.setStatus(Status.PAID);

        orderDAO.payment(order);

        Mockito.verify(mockPreparedStatement).setInt(1, order.getId());
        Mockito.verify(mockPreparedStatement).setInt(2, order.getBuyerId());
        Mockito.verify(mockPreparedStatement).setDouble(3, order.getPrice());
        Mockito.verify(mockPreparedStatement).setDate(4, order.getDate());
        Mockito.verify(mockPreparedStatement).setString(5, order.getStatus().toString());
        Mockito.verify(mockPreparedStatement).setInt(6, order.getId());
        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
    }

}