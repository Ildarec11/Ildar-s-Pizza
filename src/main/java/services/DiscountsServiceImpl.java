package services;

import models.DiscountModel;
import repositories.DiscountsRepository;
import repositories.DishRepository;

import java.util.Optional;

public class DiscountsServiceImpl implements DiscountsService {

    private DiscountsRepository discountsRepository;

    public DiscountsServiceImpl(DiscountsRepository discountsRepository) {
        this.discountsRepository = discountsRepository;
    }

    @Override
    public void save(DiscountModel discountModel) {
          discountsRepository.save(discountModel);
    }

    @Override
    public Optional<DiscountModel> getMaxDiscountByDishId(int dishId) {
        return discountsRepository.getMaxDiscountByDishId(dishId);
    }

}
