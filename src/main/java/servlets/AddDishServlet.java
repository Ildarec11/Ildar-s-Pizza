package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import forms.DishForm;
import repositories.DishRepository;
import repositories.DishRepositoryImpl;
import services.DishService;
import services.DishServiceImpl;

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

@WebServlet("/add-dish")
public class AddDishServlet extends HttpServlet {

    private DishService dishService;
    private final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        DishForm dishForm = objectMapper.readValue(req.getParameter("dish"), DishForm.class);
        dishService.addDish(dishForm);
    }

    @Override
    public void init() throws ServletException {
        super.init();
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
}
