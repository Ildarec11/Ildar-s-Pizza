package services;

import forms.DishForm;
import models.DishModel;

import java.util.List;

public interface DishService {
    List<DishModel> getAllDishes();

    void addDish(DishForm dishForm);
}
