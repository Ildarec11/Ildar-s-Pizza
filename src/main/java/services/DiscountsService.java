package services;

import models.DiscountModel;

import java.util.Optional;

public interface DiscountsService {

    void save(DiscountModel discountModel);

    Optional<DiscountModel> getMaxDiscountByDishId(int dishId);

}
