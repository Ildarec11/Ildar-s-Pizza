package filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.AuthModel;
import repositories.AuthRepository;
import repositories.AuthRepositoryImpl;
import repositories.DishRepository;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebFilter("/buy-pizza")
public class MoneyFilter extends HttpFilter {

    private AuthRepository authRepository;
    private DishRepository dishRepository;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "postgres";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest  = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
        Cookie[] cookies = httpServletRequest.getCookies();
        System.out.println("Money filter");
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                AuthModel authModel = authRepository.findByCookieValue(cookie.getValue());
                if (authModel != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    long productCost = objectMapper.readValue(req.getParameter("productCost"), Long.class);
                    BigDecimal cost = BigDecimal.valueOf(productCost);
                    if (authModel.getUserModel().getMoney().compareTo(cost) <= 0) {
                        //TODO уведомление о нехватке денег
                        System.out.println("Not enough money");
                    } else {
                        chain.doFilter(req, res);
                    }
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
