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

public class BuyersServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(BuyersServlet.class);
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public BuyersServlet() {
        this(new ProductDAO(), new UserDAO());
    }

    public BuyersServlet(ProductDAO productDAO, UserDAO userDAO) {
        this.productDAO = productDAO;
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Buyer> buyerList = userDAO.allBuyers();
        logger.info(buyerList);
        req.setAttribute("buyerList", buyerList);
        req.getRequestDispatcher("/jsp/buyers.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idBuyerBlack = req.getParameter("id");
        logger.info("id buyers black list : " + idBuyerBlack);
        Buyer buyer = userDAO.getBuyer(Integer.parseInt(idBuyerBlack));
        userDAO.addOnBlacklist(buyer);
        doGet(req, resp);
    }
}
