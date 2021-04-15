package com.github.boyarsky1997.store.db;

import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Role;
import com.github.boyarsky1997.store.model.User;
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

public class UserDAOTest {
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private UserDAO userDAO;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        userDAO = new UserDAO(mockConnection);
    }

    @Test
    public void testGetAllBlacklistBuyers() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(2, 3);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn(Role.BUYER.toString(), Role.BUYER.toString());
        Mockito.when(mockResultSet.getString(3))
                .thenReturn("Roman", "Kaban");
        Mockito.when(mockResultSet.getString(4))
                .thenReturn("Zippo", "Boyar");
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("123", "234");
        Mockito.when(mockResultSet.getString(6))
                .thenReturn("555", "777");
        Mockito.when(mockResultSet.getBoolean(7))
                .thenReturn(true, true);

        List<Buyer> actual = userDAO.getAllBlacklistBuyers();

        Assert.assertEquals(actual.size(), 2);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.get(0).getId(), new Integer(2));
        Assert.assertEquals(actual.get(0).getName(), "Roman");
        Assert.assertEquals(actual.get(0).getRole(), Role.BUYER);
        Assert.assertEquals(actual.get(0).getSurname(), "Zippo");
        Assert.assertEquals(actual.get(0).getLogin(), "123");
        Assert.assertEquals(actual.get(0).getPassword(), "555");
        Assert.assertTrue(actual.get(0).isBlackList());
        Assert.assertEquals(actual.get(1).getId(), new Integer(3));
        Assert.assertEquals(actual.get(1).getName(), "Kaban");
        Assert.assertEquals(actual.get(1).getRole(), Role.BUYER);
        Assert.assertEquals(actual.get(1).getSurname(), "Boyar");
        Assert.assertEquals(actual.get(1).getLogin(), "234");
        Assert.assertEquals(actual.get(1).getPassword(), "777");
        Assert.assertTrue(actual.get(1).isBlackList());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetAllBlacklistBuyersWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Buyer> allBlacklistBuyers = userDAO.getAllBlacklistBuyers();

        Assert.assertTrue(allBlacklistBuyers.isEmpty());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetBuyer() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(2);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn(Role.BUYER.toString());
        Mockito.when(mockResultSet.getString(3))
                .thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4))
                .thenReturn("Zippo");
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("123");
        Mockito.when(mockResultSet.getString(6))
                .thenReturn("555");
        Mockito.when(mockResultSet.getBoolean(7))
                .thenReturn(true);

        Buyer actual = userDAO.getBuyer(5);

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), new Integer(2));
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getRole(), Role.BUYER);
        Assert.assertEquals(actual.getSurname(), "Zippo");
        Assert.assertEquals(actual.getLogin(), "123");
        Assert.assertEquals(actual.getPassword(), "555");
        Assert.assertTrue(actual.isBlackList());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testGetBuyerWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        Buyer actual = userDAO.getBuyer(2);

        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setInt(1, 2);
    }

    @Test
    public void testAddOnBlacklist() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Buyer buyer = new Buyer();
        buyer.setId(3);
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");
        buyer.setBlackList(true);

        userDAO.addOnBlacklist(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 3);
        Mockito.verify(mockPreparedStatement).setString(2, Role.BUYER.toString());
        Mockito.verify(mockPreparedStatement).setString(3, "roman");
        Mockito.verify(mockPreparedStatement).setString(4, "boyar");
        Mockito.verify(mockPreparedStatement).setString(5, buyer.getLogin());
        Mockito.verify(mockPreparedStatement).setString(6, buyer.getPassword());
        Mockito.verify(mockPreparedStatement).setBoolean(7, buyer.isBlackList());
        Mockito.verify(mockPreparedStatement).setInt(8, buyer.getId());
    }

    @Test
    public void testAddOnBlacklistWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);
        Buyer buyer = new Buyer();
        buyer.setId(3);
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");
        buyer.setBlackList(true);

        userDAO.addOnBlacklist(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();

    }

    @Test
    public void testAddOnBuyers() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Buyer buyer = new Buyer();
        buyer.setId(3);
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");
        buyer.setBlackList(false);

        userDAO.addOnBuyers(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 3);
        Mockito.verify(mockPreparedStatement).setString(2, Role.BUYER.toString());
        Mockito.verify(mockPreparedStatement).setString(3, "roman");
        Mockito.verify(mockPreparedStatement).setString(4, "boyar");
        Mockito.verify(mockPreparedStatement).setString(5, buyer.getLogin());
        Mockito.verify(mockPreparedStatement).setString(6, buyer.getPassword());
        Mockito.verify(mockPreparedStatement).setBoolean(7, buyer.isBlackList());
        Mockito.verify(mockPreparedStatement).setInt(8, buyer.getId());

    }

    @Test
    public void testAddOnBuyersWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);
        Buyer buyer = new Buyer();
        buyer.setId(3);
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");
        buyer.setBlackList(false);

        userDAO.addOnBuyers(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setInt(1, 3);
        Mockito.verify(mockPreparedStatement).setString(2, Role.BUYER.toString());
        Mockito.verify(mockPreparedStatement).setString(3, "roman");
        Mockito.verify(mockPreparedStatement).setString(4, "boyar");
        Mockito.verify(mockPreparedStatement).setString(5, buyer.getLogin());
        Mockito.verify(mockPreparedStatement).setString(6, buyer.getPassword());
        Mockito.verify(mockPreparedStatement).setBoolean(7, buyer.isBlackList());
        Mockito.verify(mockPreparedStatement).setInt(8, buyer.getId());
    }

    @Test
    public void testCheckExistLogin() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.getString(1))
                .thenReturn("bla");
        Mockito.when(mockResultSet.next())
                .thenReturn(true);

        boolean actual = userDAO.checkExistLogin("d");

        Assert.assertTrue(actual);

        Mockito.verify(mockPreparedStatement).setString(1, "d");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testCheckExistLoginWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        boolean actual = userDAO.checkExistLogin("d");


        Mockito.verify(mockPreparedStatement).setString(1, "d");
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testAllBuyers() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, true, false);
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(1, 2);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn(Role.BUYER.toString(), Role.BUYER.toString());
        Mockito.when(mockResultSet.getString(3))
                .thenReturn("roma", "bara");
        Mockito.when(mockResultSet.getString(4))
                .thenReturn("roman", "kaban");
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("r@gmail.com", "v@gmail.com");
        Mockito.when(mockResultSet.getString(6))
                .thenReturn("123", "567");
        Mockito.when(mockResultSet.getBoolean(7))
                .thenReturn(true, false);

        List<Buyer> actual = userDAO.allBuyers();

        Assert.assertEquals(actual.size(), 2);
        Assert.assertFalse(actual.isEmpty());
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.get(0).getId(), new Integer(1));
        Assert.assertEquals(actual.get(0).getName(), "roma");
        Assert.assertEquals(actual.get(0).getRole(), Role.BUYER);
        Assert.assertEquals(actual.get(0).getSurname(), "roman");
        Assert.assertEquals(actual.get(0).getLogin(), "r@gmail.com");
        Assert.assertEquals(actual.get(0).getPassword(), "123");
        Assert.assertTrue(actual.get(0).isBlackList());
        Assert.assertEquals(actual.get(1).getId(), new Integer(2));
        Assert.assertEquals(actual.get(1).getName(), "bara");
        Assert.assertEquals(actual.get(1).getRole(), Role.BUYER);
        Assert.assertEquals(actual.get(1).getSurname(), "kaban");
        Assert.assertEquals(actual.get(1).getLogin(), "v@gmail.com");
        Assert.assertEquals(actual.get(1).getPassword(), "567");
        Assert.assertFalse(actual.get(1).isBlackList());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testAllBuyersWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        List<Buyer> actual = userDAO.allBuyers();

        Assert.assertTrue(actual.isEmpty());
        Mockito.verify(mockPreparedStatement).executeQuery();
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
    }

    @Test
    public void testInsertUser() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Buyer buyer = new Buyer();
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");

        userDAO.insertUser(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setString(1, Role.BUYER.toString());
        Mockito.verify(mockPreparedStatement).setString(2, "roman");
        Mockito.verify(mockPreparedStatement).setString(3, "boyar");
        Mockito.verify(mockPreparedStatement).setString(4, buyer.getLogin());
        Mockito.verify(mockPreparedStatement).setString(5, buyer.getPassword());
    }

    @Test
    public void testInsertUserWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.execute())
                .thenThrow(SQLException.class);
        Buyer buyer = new Buyer();
        buyer.setRole(Role.BUYER);
        buyer.setName("roman");
        buyer.setSurname("boyar");
        buyer.setLogin("12");
        buyer.setPassword("ssa");

        userDAO.insertUser(buyer);

        Mockito.verify(mockPreparedStatement).execute();
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeQuery();
        Mockito.verify(mockPreparedStatement).setString(1, Role.BUYER.toString());
        Mockito.verify(mockPreparedStatement).setString(2, "roman");
        Mockito.verify(mockPreparedStatement).setString(3, "boyar");
        Mockito.verify(mockPreparedStatement).setString(4, buyer.getLogin());
        Mockito.verify(mockPreparedStatement).setString(5, buyer.getPassword());
    }

    @Test
    public void testGetWhenRoleEqualsBuyer() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, false);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn(Role.BUYER.toString());
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(1);
        Mockito.when(mockResultSet.getString(3))
                .thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4))
                .thenReturn("Boyar");
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("r@gmail.com");
        Mockito.when(mockResultSet.getString(6))
                .thenReturn("123");
        Mockito.when(mockResultSet.getBoolean(7))
                .thenReturn(true);

        User actual = userDAO.get("roman", "123");

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), new Integer(1));
        Assert.assertEquals(actual.getRole(), Role.BUYER);
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getSurname(), "Boyar");
        Assert.assertEquals(actual.getLogin(), "r@gmail.com");
        Assert.assertEquals(actual.getPassword(), "123");
        Assert.assertTrue(actual.isBlackList());
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setString(1, "roman");
        Mockito.verify(mockPreparedStatement).setString(2, "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetWhenRoleEqualsAdmin() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenReturn(mockResultSet);
        Mockito.when(mockResultSet.next())
                .thenReturn(true, false);
        Mockito.when(mockResultSet.getString(2))
                .thenReturn(Role.ADMIN.toString());
        Mockito.when(mockResultSet.getInt(1))
                .thenReturn(1);
        Mockito.when(mockResultSet.getString(3))
                .thenReturn("Roman");
        Mockito.when(mockResultSet.getString(4))
                .thenReturn("Boyar");
        Mockito.when(mockResultSet.getString(5))
                .thenReturn("r@gmail.com");
        Mockito.when(mockResultSet.getString(6))
                .thenReturn("123");
        Mockito.when(mockResultSet.getBoolean(7))
                .thenReturn(true);

        User actual = userDAO.get("roman", "123");

        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getId(), new Integer(1));
        Assert.assertEquals(actual.getRole(), Role.ADMIN);
        Assert.assertEquals(actual.getName(), "Roman");
        Assert.assertEquals(actual.getSurname(), "Boyar");
        Assert.assertEquals(actual.getLogin(), "r@gmail.com");
        Assert.assertEquals(actual.getPassword(), "123");
        Assert.assertTrue(actual.isBlackList());
        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setString(1, "roman");
        Mockito.verify(mockPreparedStatement).setString(2, "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }

    @Test
    public void testGetWhenException() throws SQLException {
        Mockito.when(mockConnection.prepareStatement(Mockito.anyString()))
                .thenReturn(mockPreparedStatement);
        Mockito.when(mockPreparedStatement.executeQuery())
                .thenThrow(SQLException.class);

        User actual = userDAO.get("roman", "123");

        Mockito.verify(mockPreparedStatement, Mockito.never()).execute();
        Mockito.verify(mockPreparedStatement).setString(1, "roman");
        Mockito.verify(mockPreparedStatement).setString(2, "123");
        Mockito.verify(mockPreparedStatement).executeQuery();
    }
}
