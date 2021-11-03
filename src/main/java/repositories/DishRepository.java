package repositories;

import forms.DishForm;
import models.DishModel;

public interface DishRepository extends CrudRepository<DishModel> {
    void addDish(DishForm dishForm);
}
