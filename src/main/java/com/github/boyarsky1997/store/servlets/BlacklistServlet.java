package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.ProductDAO;
import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.Buyer;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BlacklistServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BlacklistServlet.class);
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public BlacklistServlet() {
        this(new ProductDAO(), new UserDAO());
    }

    public BlacklistServlet(ProductDAO productDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Buyer> allBlacklistBuyers = userDAO.getAllBlacklistBuyers();
        req.setAttribute("allBlacklistBuyers", allBlacklistBuyers);
        req.getRequestDispatcher("/jsp/blacklist.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Buyer buyer = userDAO.getBuyer(Integer.parseInt(id));
        userDAO.addOnBuyers(buyer);
        logger.info("id: "+ buyer.getId());
        doGet(req, resp);
    }
}
