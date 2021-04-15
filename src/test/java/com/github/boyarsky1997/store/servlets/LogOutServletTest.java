package com.github.boyarsky1997.store.servlets;

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

import static org.testng.Assert.*;

public class LogOutServletTest {

    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private LogOutServlet logOutServlet ;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        logOutServlet = new LogOutServlet();
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }
    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);

        logOutServlet.doGet(mockRequest,mockResponse);

        Mockito.verify(mockSession).invalidate();
        Mockito.verify(mockResponse).sendRedirect("/");
    }

}