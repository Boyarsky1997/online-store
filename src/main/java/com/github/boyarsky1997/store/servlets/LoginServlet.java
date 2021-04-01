package com.github.boyarsky1997.store.servlets;

import com.github.boyarsky1997.store.db.UserDAO;
import com.github.boyarsky1997.store.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LoginServlet.class);
    private final UserDAO userDAO;

    public LoginServlet() {
        this(new UserDAO());
    }

    public LoginServlet(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        System.out.println(email + password);
        User client = userDAO.get(email, password);
        logger.info(client);
        if (client == null) {
            req.setAttribute("unfaithful", "Incorrect login or password");
            req.getRequestDispatcher("/jsp/login.jsp").include(req, resp);

        } else {
            HttpSession session = req.getSession(true);
            session.setAttribute("client", client);
            resp.sendRedirect("/profile");
        }
    }
}
