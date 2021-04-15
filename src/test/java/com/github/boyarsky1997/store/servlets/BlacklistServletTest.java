package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.Buyer;
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
import java.util.Arrays;
import java.util.List;

public class BlacklistServletTest {

    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private ProductDAO mockProductDAO;
    @Mock
    private UserDAO mockUserDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private BlacklistServlet spyBlacklistServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        spyBlacklistServlet = Mockito.spy(new BlacklistServlet(mockProductDAO, mockUserDAO));
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<Buyer> allBlacklistBuyers = Arrays.asList(new Buyer(), new Buyer(), new Buyer());
        Mockito.when(mockUserDAO.getAllBlacklistBuyers())
                .thenReturn(allBlacklistBuyers);

        spyBlacklistServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("allBlacklistBuyers", allBlacklistBuyers);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/blacklist.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter(Mockito.anyString()))
                .thenReturn("2");
        Buyer buyer = new Buyer();
        buyer.setId(5);
        Mockito.when(mockUserDAO.getBuyer(Mockito.anyInt()))
                .thenReturn(buyer);

        spyBlacklistServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(spyBlacklistServlet).doGet(mockRequest,mockResponse);
    }

}