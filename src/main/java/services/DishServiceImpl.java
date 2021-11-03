package services;

import forms.DishForm;
import models.DishModel;
import repositories.DishRepository;

import java.util.List;

public class DishServiceImpl implements DishService{

    private DishRepository dishRepository;

    public DishServiceImpl(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Override
    public List<DishModel> getAllDishes() {
        return dishRepository.findAll();
    }

    @Override
    public void addDish(DishForm dishForm) {
        dishRepository.addDish(dishForm);
    }

}
