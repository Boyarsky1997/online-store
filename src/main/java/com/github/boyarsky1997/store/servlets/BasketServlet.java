package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.model.Product;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class BasketServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(BasketServlet.class);
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public BasketServlet() {
        this(new ProductDAO(), new OrderDAO());
    }

    public BasketServlet(ProductDAO productDAO, OrderDAO orderDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Buyer client =(Buyer)session.getAttribute("client");
        List<Order> orderList = orderDAO.getAllOrderInBuyerId(client.getId());
        logger.info(orderList);
        req.setAttribute("orderList", orderList);
        req.getRequestDispatcher("/jsp/basket.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/jsp/basket.jsp").forward(req, resp);
    }
}
