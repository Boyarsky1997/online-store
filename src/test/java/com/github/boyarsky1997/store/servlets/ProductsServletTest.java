package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Buyer;
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
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class ProductsServletTest {
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
    @Mock
    private Supplier<Date> mockDateSupplier;

    private ProductsServlet spyProductsServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        spyProductsServlet = Mockito.spy(new ProductsServlet(mockProductDAO, mockOrderDAO, mockDateSupplier));
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        List<Product> productList = Arrays.asList(new Product(), new Product(), new Product());
        Mockito.when(mockProductDAO.getAll())
                .thenReturn(productList);
        spyProductsServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockRequest).setAttribute("productList", productList);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/products.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPostWhenCountProductEqualsZero() throws ServletException, IOException {
        Mockito.when(mockRequest.getParameter(Mockito.anyString()))
                .thenReturn("3");
        Product product = new Product();
        product.setCount(0);
        Mockito.when(mockProductDAO.getById(Mockito.anyInt()))
                .thenReturn(product);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(new Buyer());

        spyProductsServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(spyProductsServlet).doPost(mockRequest, mockResponse);
    }

    @Test
    public void testDoPostWhenCountProductEquals() throws ServletException, IOException {
        String id = "3";
        Mockito.when(mockRequest.getParameter("id"))
                .thenReturn(id);
        Product product = new Product();
        product.setPrice(2.2);
        product.setCount(7);
        Mockito.when(mockProductDAO.getById(Mockito.anyInt()))
                .thenReturn(product);
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Buyer buyer = new Buyer();
        buyer.setId(1);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(buyer);
        Mockito.when(mockOrderDAO.getExistingOpenOrder(Mockito.anyInt()))
                .thenReturn(-1);
        int orderId = 1;
        Mockito.when(mockOrderDAO.createNewOrder(buyer.getId(), product.getPrice(), mockDateSupplier.get()))
                .thenReturn(orderId);

        spyProductsServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockProductDAO).updateProduct(product);
        Mockito.verify(mockOrderDAO).addProductInOrder(Integer.parseInt(id),orderId);
        Mockito.verify(spyProductsServlet).doGet(mockRequest, mockResponse);
    }

}