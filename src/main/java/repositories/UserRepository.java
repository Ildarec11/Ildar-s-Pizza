package repositories;

import models.UserModel;

import java.math.BigDecimal;

public interface UserRepository extends CrudRepository<UserModel>{
    UserModel findByLogin(String email);
    void spendMoney(UserModel userModel, BigDecimal money);
}
