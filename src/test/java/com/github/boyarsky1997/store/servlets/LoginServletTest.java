package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Role;
import com.github.boyarsky1997.store.model.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServletTest {
    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private UserDAO mockUserDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private LoginServlet spyLoginServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        spyLoginServlet = Mockito.spy(new LoginServlet(mockUserDAO));
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoPostWhenBlacklistEqualsFalse() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn("123");
        Mockito.when(mockRequest.getParameter("email"))
                .thenReturn("log");
        User client = new Buyer();
        client.setId(2);
        client.setRole(Role.BUYER);
        client.setBlackList(false);
        Mockito.when(mockUserDAO.get("log", "123"))
                .thenReturn(client);
        Mockito.when(mockRequest.getSession(true))
                .thenReturn(mockSession);

        spyLoginServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockSession).setAttribute("client", client);
        Mockito.verify(mockResponse).sendRedirect("/");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("unfaithful", "Incorrect login or password");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("blacklist", "This account is blacklisted");
    }

    @Test
    public void testDoPostWhenBlacklistEqualsTrue() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn("123");
        Mockito.when(mockRequest.getParameter("email"))
                .thenReturn("log");
        User client = new Buyer();
        client.setId(2);
        client.setRole(Role.BUYER);
        client.setBlackList(true);
        Mockito.when(mockUserDAO.get("log", "123"))
                .thenReturn(client);
        Mockito.when(mockRequest.getSession(true))
                .thenReturn(mockSession);

        spyLoginServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockSession, Mockito.never()).setAttribute("client", client);
        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("unfaithful", "Incorrect login or password");
        Mockito.verify(mockRequest).setAttribute("blacklist", "This account is blacklisted");
    }

    @Test
    public void testDoPostWhenUserEqualsNull() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn("123");
        Mockito.when(mockRequest.getParameter("email"))
                .thenReturn("log");
        Mockito.when(mockUserDAO.get("log", "123"))
                .thenReturn(null);
        Mockito.when(mockRequest.getSession(true))
                .thenReturn(mockSession);

        spyLoginServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/");
        Mockito.verify(mockRequest).setAttribute("unfaithful", "Incorrect login or password");
        Mockito.verify(mockRequest, Mockito.never()).setAttribute("blacklist", "This account is blacklisted");
    }

    @Test
    public void testDoGet() throws ServletException, IOException {

        spyLoginServlet.doGet(mockRequest,mockResponse);

        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/login.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
    }

}