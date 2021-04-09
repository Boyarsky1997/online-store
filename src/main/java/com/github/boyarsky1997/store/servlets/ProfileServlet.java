package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.model.Product;
import com.github.boyarsky1997.store.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ProfileServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ProfileServlet.class);
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public ProfileServlet() {
        this(new ProductDAO(), new UserDAO(), new OrderDAO());
    }

    public ProfileServlet(ProductDAO productDAO, UserDAO userDAO, OrderDAO orderDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        session.getAttribute("client");
        Buyer buyer = (Buyer) session.getAttribute("client");
        List<Order> paidOrders = orderDAO.getPaidProducts(buyer.getId());
        double sum = 0;
        for (Order paidOrder : paidOrders) {
            List<Product> allProductOnOrderId = productDAO.getAllProductOnOrderId(paidOrder.getId());
            for (Product product : allProductOnOrderId) {
                sum = sum + product.getPrice();
            }
            paidOrder.setPrice(sum);
        }
        req.setAttribute("paidOrders", paidOrders);
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/profile.jsp").forward(req, resp);
    }
}
