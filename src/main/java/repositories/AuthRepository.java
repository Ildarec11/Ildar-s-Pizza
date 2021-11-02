package repositories;

import models.AuthModel;
import models.UserModel;

import javax.servlet.http.Cookie;

public interface AuthRepository  extends CrudRepository<AuthModel>{
    AuthModel findByCookieValue(Cookie cookie);
    boolean checkIsAdminByCookieValue(Cookie cookie);
    void setCookiesForUser(UserModel userModel, Cookie cookie);
}
