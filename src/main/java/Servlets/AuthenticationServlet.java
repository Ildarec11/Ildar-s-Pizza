package Servlets;

import Services.UserService;
import Services.UserServiceImpl;
import forms.SignInUserForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repositories.AuthRepository;
import repositories.AuthRepositoryImpl;
import repositories.UserRepository;
import repositories.UserRepositoryImpl;

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

@WebServlet("/signIn")
public class AuthenticationServlet extends HttpServlet {

    private UserService userService;
    private final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "postgres";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/SignIn.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        SignInUserForm signInUserForm = new SignInUserForm();
        signInUserForm.setEmail(req.getParameter("email"));
        signInUserForm.setPassword(req.getParameter("password"));
        Cookie cookie = userService.signIn(signInUserForm);

        if (cookie != null) {
            resp.addCookie(cookie);
            resp.sendRedirect("/menu");
        } else {
            req.getRequestDispatcher("WEB-INF/jsp/SignIn.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
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
}
