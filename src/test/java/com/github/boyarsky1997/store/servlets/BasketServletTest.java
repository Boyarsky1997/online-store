package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.*;
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

public class BasketServletTest {

    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private ProductDAO mockProductDAO;
    @Mock
    private OrderDAO mockOrderDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private BasketServlet spyBasketServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        spyBasketServlet = Mockito.spy(new BasketServlet(mockProductDAO, mockOrderDAO));
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Buyer buyer = new Buyer();
        buyer.setId(2);
        buyer.setRole(Role.BUYER);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(buyer);
        int id = 1;
        Mockito.when(mockOrderDAO.getExistingOpenOrder(Mockito.anyInt()))
                .thenReturn(id);
        Product product = new Product();
        product.setPrice(33);
        Product product1 = new Product();
        product1.setPrice(34);
        List<Product> productList = Arrays.asList(product, product1);
        Mockito.when(mockProductDAO.getAllProductOnOrderId(Mockito.anyInt()))
                .thenReturn(productList);

        spyBasketServlet.doGet(mockRequest, mockResponse);
        double sum = 67;
        Mockito.verify(mockRequest).setAttribute("sum", sum);
        Mockito.verify(mockRequest).setAttribute("productList", productList);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/basket.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
    }

    @Test
    public void testDoPostWhenActionEqualsBuy() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter(Mockito.anyString()))
                .thenReturn("buy");
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Buyer buyer = new Buyer();
        buyer.setId(2);
        buyer.setRole(Role.BUYER);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(buyer);
        Order order = new Order();
        order.setId(3);
        order.setStatus(Status.NEW);
        Mockito.when(mockOrderDAO.getAllOrderInBuyerId(Mockito.anyInt()))
                .thenReturn(order);
        order.setStatus(Status.PAID);

        spyBasketServlet.doPost(mockRequest,mockResponse);

        Mockito.verify(mockOrderDAO).payment(order);
        Mockito.verify(spyBasketServlet).doGet(mockRequest,mockResponse);
    }
}