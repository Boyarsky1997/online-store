package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Order;
import com.github.boyarsky1997.store.model.Product;
import com.github.boyarsky1997.store.model.Status;
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
        Buyer client = (Buyer) session.getAttribute("client");
        int id = orderDAO.getExistingOpenOrder(client.getId());
        logger.info("get " + id);
        List<Product> productList = productDAO.getAllProductOnOrderId(id);
        logger.info("get " + productList);
        double sum = 0;
        for (Product product : productList) {
            sum = sum + product.getPrice();
        }
        req.setAttribute("sum", sum);
        req.setAttribute("productList", productList);
        req.getRequestDispatcher("/jsp/basket.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        logger.info(action);
        if (action.equals("buy")) {
            HttpSession session = req.getSession(false);
            Buyer client = (Buyer) session.getAttribute("client");
            Order order = orderDAO.getAllOrderInBuyerId(client.getId());
            logger.info("post " + order.getId());
            order.setStatus(Status.PAID);
            orderDAO.payment(order);
        } else {
            HttpSession session = req.getSession(false);
            Buyer client = (Buyer) session.getAttribute("client");
            int id = orderDAO.getExistingOpenOrder(client.getId());
//            List<Product> productList = productDAO.getAllProductOnOrderId(id);
            String idProduct = req.getParameter("id");
            orderDAO.removeProductFromOrder(Integer.parseInt(idProduct),id);
//            logger.info(idProduct);
//
//            for (int i = 0; i < productList.size(); i++) {
//                if (productList.get(i).getId() == Integer.parseInt(idProduct)) {
//                    productList.remove(productList.get(i));
//                }
//            }
//            double sum = 0;
//            for (Product product : productList) {
//                sum = sum + product.getPrice();
//            }
//            req.setAttribute("sum", sum);
//            req.setAttribute("productList", productList);
        }

        doGet(req, resp);
    }
}
