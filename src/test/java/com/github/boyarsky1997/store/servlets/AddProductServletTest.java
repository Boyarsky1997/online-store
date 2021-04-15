package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Admin;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Role;
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

public class AddProductServletTest {

    @Mock
    private HttpSession mockSession;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private ProductDAO mockProductDAO;
    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private AddProductServlet addProductServlet;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
        addProductServlet = new AddProductServlet(mockProductDAO);
        Mockito.when(mockRequest.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(mockRequestDispatcher);
    }

    @Test
    public void testToGetWhenRoleEqualsAdmin() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Admin admin = new Admin();
        admin.setId(2);
        admin.setRole(Role.ADMIN);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(admin);

        addProductServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse, Mockito.never()).sendError(403);
        Mockito.verify(mockRequest).getRequestDispatcher("/jsp/addition.jsp");
        Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);
    }

    @Test
    public void testToGetWhenRoleEqualsBuyer() throws ServletException, IOException {
        Mockito.when(mockRequest.getSession(false))
                .thenReturn(mockSession);
        Buyer buyer = new Buyer();
        buyer.setId(2);
        buyer.setRole(Role.BUYER);
        Mockito.when(mockSession.getAttribute(Mockito.anyString()))
                .thenReturn(buyer);

        addProductServlet.doGet(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendError(403);
        Mockito.verify(mockRequest, Mockito.never()).getRequestDispatcher("/jsp/addition.jsp");
        Mockito.verify(mockRequestDispatcher, Mockito.never()).forward(mockRequest, mockResponse);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        String name = "name";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String price = "";
        Mockito.when(mockRequest.getParameter("price"))
                .thenReturn(price);
        String count = "count";
        Mockito.when(mockRequest.getParameter("count"))
                .thenReturn(count);
        String description = "description";
        Mockito.when(mockRequest.getParameter("description"))
                .thenReturn(description);

        addProductServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse).sendRedirect("/addition");
        Mockito.verify(mockResponse, Mockito.never()).sendRedirect("/products");
        Mockito.verify(mockProductDAO, Mockito.never())
                .addProduct(name, 0, 4, description);

    }

    @Test
    public void testDoPostWithoutAPass() throws ServletException, IOException {
        String name = "name";
        Mockito.when(mockRequest.getParameter("name"))
                .thenReturn(name);
        String price = "2";
        Mockito.when(mockRequest.getParameter("price"))
                .thenReturn(price);
        String count = "3";
        Mockito.when(mockRequest.getParameter("count"))
                .thenReturn(count);
        String description = "description";
        Mockito.when(mockRequest.getParameter("description"))
                .thenReturn(description);

        addProductServlet.doPost(mockRequest, mockResponse);

        Mockito.verify(mockResponse,Mockito.never()).sendRedirect("/addition");
        Mockito.verify(mockResponse).sendRedirect("/products");
        Mockito.verify(mockProductDAO)
                .addProduct(name, Double.parseDouble(price), Integer.parseInt(count), description);

    }

}