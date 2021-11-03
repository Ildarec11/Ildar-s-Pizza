package services;

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

}
