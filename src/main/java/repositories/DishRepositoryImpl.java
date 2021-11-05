package repositories;

import forms.DishForm;
import mapper.RowMapper;
import models.DiscountModel;
import models.DishModel;
import models.PurchaseHistoryModel;
import models.UserModel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DishRepositoryImpl implements DishRepository {

    private Connection connection;

    private final String SQL_FIND_ALL = "SELECT * FROM dish";
    private final String SQL_FIND_DISH = "SELECT * FROM dish WHERE id = ?";
    private final String SQL_INSERT_DISH = "INSERT INTO dish(name, description, cost) values (?, ?, ?)";
    private final String SQL_ADD_DISH_TO_HISTORY = "INSERT INTO purchase_history(user_id, dish_id, cost, discount_percents, purchase_date) VALUES (?, ?, ?, ?, ?)";
    private final String SQL_GET_PURCHASE_HISTORY = "SELECT * FROM purchase_history WHERE user_id = ?";

    public DishRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DishModel> findAll() {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<DishModel> result = rowMapDishes.rowMap(resultSet);
            return result;
        } catch (Exception e) {
            System.out.println("Cant get dishes");
            return null;
        }
    }

    @Override
    public Optional<DishModel> findById() {
        return Optional.empty();
    }

    @Override
    public DishModel findById(int dishId) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_DISH);
            preparedStatement.setInt(1, dishId);
            resultSet = preparedStatement.executeQuery();
            DishModel result = rowMapDishes.rowMap(resultSet).get(0);
            return result;
        } catch (Exception e) {
            System.out.println("Cant get dishes");
            return null;
        }
    }

    @Override
    public DishModel save(DishModel dishModel) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    private RowMapper<List<DishModel>> rowMapDishes = ((resultSet) -> {
        List<DishModel> products = new ArrayList<>();
        while (resultSet.next()) {
            DishModel dish = new DishModel();
            dish.setId(resultSet.getInt("id"));
            dish.setName(resultSet.getString("name"));
            dish.setCost(resultSet.getBigDecimal("cost"));
            dish.setDescription(resultSet.getString("description"));
            products.add(dish);
        }
        return products;
    });

    private RowMapper<List<PurchaseHistoryModel>> rowMapHistory = ((resultSet) -> {
        List<PurchaseHistoryModel> history = new ArrayList<>();
        while (resultSet.next()) {
            PurchaseHistoryModel historyModel = new PurchaseHistoryModel();
            historyModel.setUserId(resultSet.getInt("user_id"));
            historyModel.setDishId(resultSet.getInt("dish_id"));
            historyModel.setDiscountsPercents(resultSet.getInt("discount_percents"));
            historyModel.setPurchaseDate(resultSet.getDate("purchase_date"));
            historyModel.setCost(resultSet.getBigDecimal("cost"));
            history.add(historyModel);
        }
        return history;
    });

    @Override
    public void addDish(DishForm dishForm) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_DISH);
            preparedStatement.setString(1, dishForm.getName());
            preparedStatement.setString(2, dishForm.getDescription());
            preparedStatement.setInt(3, dishForm.getCost());
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("Cant add dishes");
        }
    }

    @Override
    public void writeDishToPurchaseHistory(UserModel userModel, DishModel dishModel, BigDecimal cost, int discountPercentage) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_DISH_TO_HISTORY);
            preparedStatement.setInt(1, userModel.getId());
            preparedStatement.setInt(2, dishModel.getId());
            preparedStatement.setBigDecimal(3, dishModel.getCost());
            preparedStatement.setInt(4, discountPercentage);
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));
            preparedStatement.execute();
        } catch (Exception e) {
            System.out.println("Cant write to purchase history");
        }
    }

    @Override
    public List<PurchaseHistoryModel> getPurchaseHistoryByUserId(int userId) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_PURCHASE_HISTORY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<PurchaseHistoryModel> result = rowMapHistory.rowMap(resultSet);
            return result;
        } catch (Exception e) {
            System.out.println("Cant get purchase history");
        }
        return new ArrayList<>();
    }
}
