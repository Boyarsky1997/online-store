package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.model.Product;
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
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

public class ProfileServletTest {
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

    private ProfileServlet profileServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        profileServlet = new ProfileServlet(mockProductDAO,mockOrderDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Buyer buyer = new Buyer();
        buyer.setId(3);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(buyer);
        Order order = new Order();
        order.setPrice(2);
        List<Order> paidOrders = Collections.singletonList(order);
        Mockito.when(mockOrderDAO.getPaidProducts(Mockito.anyInt()))
                .thenReturn(paidOrders);
        Product product = new Product();
        Product product1 = new Product();
        product.setPrice(22);
        product1.setPrice(32);
        List<Product> productList = Arrays.asList(product, product1);
        Mockito.when(mockProductDAO.getAllProductOnOrderId(Mockito.anyInt()))
                .thenReturn(productList);
        paidOrders.get(0).setPrice(54);

        profileServlet.doGet(mockRequest,mockResponse);

        Mockito.verify(mockRequest).setAttribute("paidOrders",paidOrders);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        profileServlet.doPost(mockRequest,mockResponse);

        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/profile.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest,mockResponse);
    }

}