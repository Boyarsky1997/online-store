package com.github.boyarsky1997.store.filter;


import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthorizationFilter implements Filter {
    private static final Logger logger = Logger.getLogger(com.github.boyarsky1997.store.filter.AuthorizationFilter.class);

    private List<String> blacklists;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        blacklists = Arrays.asList(filterConfig.getInitParameter("blacklist").split(","));
        logger.info("Filters init params: " + blacklists);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("client") == null) {
            for (String value : blacklists) {
                if (request.getRequestURI().endsWith(value)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }

            ((HttpServletResponse) servletResponse).sendRedirect("/login");
            logger.info("Redirected - No session");
            return;
        }

        if (request.getRequestURI().endsWith("/login") || request.getRequestURI().endsWith("/registration")) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect("/profile");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("Пройшов фільтр");
    }

    @Override
    public void destroy() {

    }
}
