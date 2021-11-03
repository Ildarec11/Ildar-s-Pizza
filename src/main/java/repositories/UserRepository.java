package repositories;

import models.UserModel;

import javax.servlet.http.Cookie;

public interface UserRepository extends CrudRepository<UserModel>{
    UserModel findByLogin(String email);
}
