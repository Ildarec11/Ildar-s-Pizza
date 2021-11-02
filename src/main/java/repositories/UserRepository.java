package repositories;

import models.UserModel;

public interface UserRepository extends CrudRepository<UserModel>{
    UserModel findByLogin(String email);
}
