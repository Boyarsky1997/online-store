package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.OrderDAO;
import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Buyer;
import com.github.boyarsky1997.store.model.Product;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.function.Supplier;

public class ProductsServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ProductsServlet.class);
    private final static Calendar calendar = Calendar.getInstance();
    private final Supplier<Date> dateSupplier;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;

    public ProductsServlet() {
        this(new ProductDAO(), new OrderDAO(), () -> new Date(calendar.getTime().getTime()));
    }

    public ProductsServlet(ProductDAO productDAO, OrderDAO orderDao, Supplier<Date> dateSupplier) {
        this.productDAO = productDAO;
        this.orderDAO = orderDao;
        this.dateSupplier = dateSupplier;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Product> productList = productDAO.getAll();
        logger.info(productList);
        req.setAttribute("productList", productList);

        req.getRequestDispatcher("/jsp/products.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Product product = productDAO.getById(Integer.parseInt(id));
        HttpSession session = req.getSession(false);
        Buyer client = (Buyer) session.getAttribute("client");
        if (product.getCount() == 0) {
            doGet(req, resp);
            return;
        }
        int tmp = product.getCount() - 1;
        product.setCount(tmp);
        productDAO.updateProduct(product);
        int orderId = orderDAO.getExistingOpenOrder(client.getId());
        if (orderId == -1) {
            orderId = orderDAO.createNewOrder(client.getId(), product.getPrice(), dateSupplier.get());
        }

        orderDAO.addProductInOrder(Integer.parseInt(id), orderId);
        doGet(req, resp);
    }

}
