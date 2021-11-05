package repositories;

import forms.DishForm;
import models.DiscountModel;
import models.DishModel;
import models.PurchaseHistoryModel;
import models.UserModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishRepository extends CrudRepository<DishModel> {
    DishModel findById(int dishId);
    void addDish(DishForm dishForm);
    void writeDishToPurchaseHistory(UserModel userModel, DishModel dishModel, BigDecimal cost, int discountPercentage);
    List<PurchaseHistoryModel> getPurchaseHistoryByUserId(int userId);
}
