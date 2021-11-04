package services;

import forms.DishForm;
import models.DishModel;
import models.PurchaseHistoryModel;

import java.util.List;

public interface DishService {
    List<DishModel> getAllDishes();
    DishModel findById(int dishId);
    void addDish(DishForm dishForm);
    List<PurchaseHistoryModel> getPurchaseHistoryByUserId(int userId);
}
