package repositories;

import mapper.RowMapper;
import models.AuthModel;
import models.UserModel;

import javax.servlet.http.Cookie;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthRepositoryImpl implements AuthRepository{

    private Connection connection;

    private final String SQL_FIND_BY_COOKIE_VALUE = "SELECT *, auth.id as auth_id, users.id as user_id FROM auth INNER JOIN users ON auth.user_id=users.id WHERE auth.cookie_value=?";
    private final String SQL_INSERT_ADD_COOKIES = "INSERT INTO auth (user_id, cookie_value)" +
            "VALUES(? , ? ) " +
            "ON CONFLICT (user_id) " +
            "DO" +
            "   UPDATE SET cookie_value = ?";
    private final String SQL_CHECK_IS_ADMIN_BY_ID = "SELECT * FROM admins_user_id WHERE user_id = ?";


    public AuthRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public AuthModel findByCookieValue(String cookieValue) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_COOKIE_VALUE);
            preparedStatement.setString(1, cookieValue);
            resultSet = preparedStatement.executeQuery();
            AuthModel auth = authRowMapper.rowMap(resultSet);
            return auth;
        } catch (Exception e) {
            System.out.println("Unable to find user");
            return null;
        }
    }

    @Override
    public boolean checkIsAdminByCookieValue(String cookieValue) {
        AuthModel authModel = findByCookieValue(cookieValue);
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHECK_IS_ADMIN_BY_ID);
            preparedStatement.setInt(1, authModel.getUserModel().getId());
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public void setCookiesForUser(UserModel userModel, String cookieValue) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ADD_COOKIES);
            preparedStatement.setInt(1, userModel.getId());
            preparedStatement.setString(2, cookieValue);
            preparedStatement.setString(3, cookieValue);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Error set cookies");
        }
    }

    @Override
    public List<AuthModel> findAll() {
        return null;
    }

    @Override
    public Optional<AuthModel> findById() {
        return Optional.empty();
    }

    @Override
    public AuthModel save(AuthModel authModel) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    private RowMapper<AuthModel> authRowMapper = (resultSet) -> {
        if (resultSet.next()) {
            AuthModel authModel = new AuthModel();
            authModel.setId(resultSet.getInt("auth_id"));
            authModel.setCookieValue(resultSet.getString("cookie_value"));

            UserModel user = new UserModel();
            user.setId(resultSet.getInt("user_id"));
            user.setFullName(resultSet.getString("full_name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            System.out.println(resultSet.getBigDecimal("money"));
            user.setMoney(resultSet.getBigDecimal("money"));
            authModel.setUserModel(user);
            return authModel;
        } else {
            return null;
        }
    };

}
