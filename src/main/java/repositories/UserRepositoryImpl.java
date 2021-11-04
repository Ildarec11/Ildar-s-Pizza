package repositories;

import models.UserModel;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {

    private Connection connection;

    private String SQL_INSERT_USER = "INSERT INTO users(full_name, email, password) VALUES (?, ?, ?)";
    private final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE email=?";
    private final String SQL_SET_COOKIE_TO_USER = "INSERT INTO auth(user_id, cookie_value) VALUES (?, ?)";
    private final String SQL_SPEND_USER_MONEY = "UPDATE users SET money = ? WHERE id = ?";


    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<UserModel> findAll() {
        return null;
    }

    @Override
    public Optional<UserModel> findById() {
        return null;
    }

    @Override
    public UserModel save(UserModel userModel) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, userModel.getFullName());
            preparedStatement.setString(2, userModel.getEmail());
            preparedStatement.setString(3, userModel.getPassword());

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                userModel.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userModel;
    }

    @Override
    public UserModel findByLogin(String login) {
        ResultSet resultSet;
        UserModel user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN);
            preparedStatement.setString(1, login);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new UserModel();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setFullName(resultSet.getString("full_name"));
                user.setMoney(resultSet.getBigDecimal("money"));
            }
        } catch (SQLException throwables) {
            System.out.println("Can't find user");
        }
        return user;
    }

    @Override
    public void spendMoney(UserModel userModel, BigDecimal money) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SPEND_USER_MONEY);
            BigDecimal updatedMoney = userModel.getMoney().subtract(money);
            preparedStatement.setBigDecimal(1, updatedMoney);
            preparedStatement.setInt(2, userModel.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println("Can't spend money");
        }
    }

    @Override
    public void deleteById(int id) {

    }
}
