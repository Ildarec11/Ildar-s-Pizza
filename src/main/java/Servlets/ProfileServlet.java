package Servlets;

import Services.UserService;
import models.AuthModel;
import models.UserModel;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProfileServlet extends HttpServlet {

    private UserService userService;
    private AuthModel authModel;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "postgres";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("auth")) {
                UserModel user = userService.getUserByCookie(cookie);
                if (user != null) {
                    req.setAttribute("user", user);
                    boolean isAdmin = userService.checkIsAdminByCookieValue(cookie);
                    if (isAdmin) {
                        req.getRequestDispatcher("WEB-INF/jsp/ProfileAdmin.jsp").forward(req, resp);
                    } else {
                        req.getRequestDispatcher("WEB-INF/jsp/Profile.jsp").forward(req, resp);
                    }
                }
            }
        }

        resp.sendRedirect("/signIn");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
    }
}
