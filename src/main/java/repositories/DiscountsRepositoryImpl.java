package repositories;

import mapper.RowMapper;
import models.AuthModel;
import models.DiscountModel;
import models.DishModel;
import models.UserModel;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountsRepositoryImpl implements DiscountsRepository {

    private Connection connection;

    private final String SQL_GET_ALL_DISCOUNTS = "SELECT * from discounts";
    private final String SQL_SAVE_DISCOUNT = "INSERT INTO discounts(dish_id, percentage, start_date, end_date) VALUES (?, ?, ?, ?)";
    private final String SQL_GET_DISCOUNT = "SELECT max(percentage) FROM discounts WHERE dish_id=? AND end_date <= ?";

    public DiscountsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DiscountModel> findAll() {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_DISCOUNTS);
            resultSet = preparedStatement.executeQuery();
            List<DiscountModel> result = discountRowMapper.rowMap(resultSet);
            return result;
        } catch (Exception e) {
            System.out.println("Cant get dishes");
            return null;
        }
    }

    @Override
    public Optional<DiscountModel> findById() {
        return Optional.empty();
    }

    @Override
    public DiscountModel save(DiscountModel discountModel) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_DISCOUNT);
            preparedStatement.setInt(1, discountModel.getDishId());
            preparedStatement.setInt(2, discountModel.getPercentage());
            ZoneId zoneId = ZoneId.of ( "Europe/Moscow" );
            Instant startDateInst = discountModel.getStartDate().toInstant();
            ZonedDateTime zdtStart = ZonedDateTime.ofInstant ( startDateInst , zoneId );
            LocalDate localDateStart = zdtStart.toLocalDate();
            preparedStatement.setDate(3, Date.valueOf(localDateStart));
            Instant endDateInst = discountModel.getEndDate().toInstant();
            ZonedDateTime ztdEnd = ZonedDateTime.ofInstant(endDateInst, zoneId);
            LocalDate localDateEnd = ztdEnd.toLocalDate();
            preparedStatement.setDate(4, Date.valueOf(localDateEnd));
            preparedStatement.execute();
            return discountModel;
        } catch (SQLException e) {
            System.out.println("Error save discount");
        }
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public Optional<DiscountModel> getMaxDiscountByDishId(int dishId) {
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_DISCOUNT);
            preparedStatement.setInt(1, dishId);
            preparedStatement.setDate(2, Date.valueOf(LocalDate.now()));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.ofNullable(rowMapDiscount.rowMap(resultSet));
            }
            return Optional.empty();
        } catch (Exception e) {
            System.out.println("Cant write to purchase history");
        }
        return Optional.empty();
    }


    private RowMapper<List<DiscountModel>> discountRowMapper = (resultSet) -> {
        List<DiscountModel> discounts = new ArrayList<>();
        while (resultSet.next()) {
            DiscountModel discountModel = new DiscountModel();
            discountModel.setId(resultSet.getInt("id"));
            discountModel.setDishId(resultSet.getInt("dish_id"));
            discountModel.setPercentage(resultSet.getInt("percentage"));
            discountModel.setStartDate(resultSet.getDate("start_date"));
            discountModel.setEndDate(resultSet.getDate("end_date"));
            discounts.add(discountModel);
        }
        return discounts;
    };

    private  RowMapper<DiscountModel> rowMapDiscount = ((resultSet) -> {
        DiscountModel discountModel = new DiscountModel();
        discountModel.setDishId(resultSet.getInt("dish_id"));
        discountModel.setId(resultSet.getInt("id"));
        discountModel.setPercentage(resultSet.getInt("percentage"));
        discountModel.setStartDate(resultSet.getDate("start_date"));
        discountModel.setEndDate(resultSet.getDate("end_date"));
        return discountModel;
    });

}
