package filters;

import Services.UserService;
import Services.UserServiceImpl;
import models.AuthModel;
import repositories.AuthRepository;
import repositories.AuthRepositoryImpl;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebFilter("/profile")
public class SignInFIlter extends HttpFilter {

    private AuthRepository authRepository;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "postgres";




    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest  = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;

        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                AuthModel authModel = authRepository.findByCookieValue(cookie);
                if (authModel != null) {
                    //вход с передачей данных о пользователе
                    ((HttpServletResponse) res).sendRedirect("/");
                    System.out.println("вошли");
                }
            }
        }
    }

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            authRepository = new AuthRepositoryImpl(connection);

        } catch (SQLException | ClassNotFoundException e) {
            throw new UnavailableException("Unavailable");
        }
    }
}
