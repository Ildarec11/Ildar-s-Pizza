package Servlets;

import Services.DishService;
import Services.DishServiceImpl;
import models.DishModel;
import repositories.DishRepository;
import repositories.DishRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {


    private DishService dishService;
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            DishRepository dishRepository = new DishRepositoryImpl(connection);
            dishService = new DishServiceImpl(dishRepository);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<DishModel> dishModels = dishService.getAllDishes();
        req.setAttribute("menu", dishModels);
        req.getRequestDispatcher("WEB-INF/jsp/Menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
