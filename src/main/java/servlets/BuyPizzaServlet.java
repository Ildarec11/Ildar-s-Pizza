package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import forms.DishForm;
import models.AuthModel;
import models.DishModel;
import repositories.*;
import services.DishService;
import services.DishServiceImpl;
import services.UserService;
import services.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/buy-pizza")
public class BuyPizzaServlet extends HttpServlet {

    private UserService userService;
    private DishService dishService;
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("auth")) {
                AuthModel authModel = userService.getAuthByCookie(cookie.getValue());
                if (authModel != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    DishModel dishModel = objectMapper.readValue(req.getParameter("boughtDish"), DishModel.class);
                    userService.buyDish(authModel.getUserModel(), dishModel);
                }
            }
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            UserRepository userRepository = new UserRepositoryImpl(connection);
            AuthRepository authRepository = new AuthRepositoryImpl(connection);
            DishRepository dishRepository = new DishRepositoryImpl(connection);
            userService = new UserServiceImpl(userRepository, authRepository, dishRepository);
            dishService = new DishServiceImpl(dishRepository);
        } catch (SQLException | ClassNotFoundException e) {
            throw new UnavailableException("Unavailable");
        }
    }
}
