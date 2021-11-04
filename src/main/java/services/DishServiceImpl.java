package services;

import forms.DishForm;
import models.DishModel;
import models.PurchaseHistoryModel;
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
    public DishModel findById(int dishId) {
        return dishRepository.findById(dishId);
    }

    @Override
    public void addDish(DishForm dishForm) {
        dishRepository.addDish(dishForm);
    }

    @Override
    public List<PurchaseHistoryModel> getPurchaseHistoryByUserId(int userId) {
        return dishRepository.getPurchaseHistoryByUserId(userId);
    }

}
