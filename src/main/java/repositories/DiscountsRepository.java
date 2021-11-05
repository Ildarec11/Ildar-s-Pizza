package repositories;

import models.DiscountModel;

import java.util.Optional;

public interface DiscountsRepository extends CrudRepository<DiscountModel> {

    Optional<DiscountModel> getMaxDiscountByDishId(int dishId);

}
