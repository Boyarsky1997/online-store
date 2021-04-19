package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.Admin;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Role;
import com.github.boyarsky1997.store.model.User;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RegistrationServletTest {
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

    private RegistrationServlet registrationServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        registrationServlet = new RegistrationServlet(mockUserDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String name = "roman";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "boyar";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "r@Ggmail.com";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);

        Role role = Role.BUYER;
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role.toString());

        registrationServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/registration");
    }

    @Test
    public void testDoPostWhenCheckExistLoginEqualsFalse() throws ServletException, IOException {
        String name = "roman";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "boyar";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "r@Ggmail.com";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "123";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);

        Role role = Role.BUYER;
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role.toString());

        Mockito.when(mockUserDAO.checkExistLogin(login))
                .thenReturn(false);
        User user = new Buyer(null, login, password, name, surname);

        registrationServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/login");
        Mockito.verify(mockUserDAO).insertUser(user);
    }

    @Test
    public void testDoPostWhenCheckExistLoginEqualsTrue() throws ServletException, IOException {
        String name = "roman";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "boyar";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "r@Ggmail.com";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "123";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);

        Role role = Role.BUYER;
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role.toString());

        Mockito.when(mockUserDAO.checkExistLogin(login))
                .thenReturn(true);
        User user = new Buyer(null, login, password, name, surname);

        registrationServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/login");
        Mockito.verify(mockUserDAO, Mockito.never()).insertUser(user);
        Mockito.verify(mockRequest).setAttribute("message", "This login already exists");
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPostWhenRoleEqualsAdmin() throws ServletException, IOException {
        String name = "roman";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String surname = "boyar";
        Mockito.when(mockRequest.getParameter("surname"))
                .thenReturn(surname);
        String login = "r@Ggmail.com";
        Mockito.when(mockRequest.getParameter("login"))
                .thenReturn(login);
        String password = "123";
        Mockito.when(mockRequest.getParameter("password"))
                .thenReturn(password);

        Role role = Role.ADMIN;
        Mockito.when(mockRequest.getParameter("role"))
                .thenReturn(role.toString());

        Mockito.when(mockUserDAO.checkExistLogin(login))
                .thenReturn(true);
        User user = new Admin(null, login, password, name, surname);


        registrationServlet.doPost(mockRequest, mockResponse);

        Assert.assertEquals(user.getRole(),Role.ADMIN);
    }

    @Test
    public void testToGet() throws ServletException, IOException {

        registrationServlet.doGet(mockRequest,mockResponse);

        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/registration.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
    }
}