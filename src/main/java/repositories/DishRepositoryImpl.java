package repositories;

import forms.DishForm;
import mapper.RowMapper;
import models.AuthModel;
import models.DishModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DishRepositoryImpl implements DishRepository {

    private Connection connection;

    private final String SQL_FIND_ALL = "SELECT * FROM dish";
    private final String SQL_INSERT_DISH = "INSERT INTO dish(name, description, cost) values (?, ?, ?)";

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
            dish.setCost(resultSet.getInt("cost"));
            dish.setDescription(resultSet.getString("description"));
            products.add(dish);
        }
        return products;
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
}
