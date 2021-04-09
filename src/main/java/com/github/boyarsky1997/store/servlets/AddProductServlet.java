package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.model.Role;
import com.github.boyarsky1997.store.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddProductServlet.class);

    private ProductDAO productDAO;

    public AddProductServlet() {
        this(new ProductDAO());
    }

    public AddProductServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User client = (User) session.getAttribute("client");
        if (!client.getRole().equals(Role.ADMIN)) {
            resp.sendError(403);
            return;
        } else {
            req.getRequestDispatcher("/jsp/addition.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String count = req.getParameter("count");
        String description = req.getParameter("description");
        logger.info(name + " " + price + " " + count + " " + description);

        if (name.equals("") || price.equals("") || count.equals("") || description.equals("")) {
            resp.sendRedirect("/addition");
        } else {
            productDAO.addProduct(name, Integer.parseInt(price), Integer.parseInt(count), description);
            logger.info(name);
            logger.info(description);
            resp.sendRedirect("/products");
        }
    }
}
