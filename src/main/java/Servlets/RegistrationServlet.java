package Servlets;

import Services.UserService;
import Services.UserServiceImpl;
import forms.SignUpUserForm;
import repositories.AuthRepository;
import repositories.AuthRepositoryImpl;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

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

@WebServlet("/signUp")
public class RegistrationServlet extends HttpServlet {

    private UserService userService;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "postgres";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            UserRepository userRepository = new UserRepositoryImpl(connection);
            AuthRepository authRepository = new AuthRepositoryImpl(connection);
            userService = new UserServiceImpl(userRepository, authRepository);
        } catch (SQLException | ClassNotFoundException e) {
            throw new UnavailableException("Unavailable");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/SignUp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        SignUpUserForm signUpUserForm = new SignUpUserForm();
        signUpUserForm.setFullName(req.getParameter("fullName"));
        signUpUserForm.setEmail(req.getParameter("email"));
        signUpUserForm.setPassword(req.getParameter("password"));

        userService.register(signUpUserForm);

        req.getRequestDispatcher("/signIn").forward(req, resp);
    }
}
