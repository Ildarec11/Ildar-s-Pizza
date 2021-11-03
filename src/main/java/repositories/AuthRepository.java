package repositories;

import models.AuthModel;
import models.UserModel;

import javax.servlet.http.Cookie;

public interface AuthRepository  extends CrudRepository<AuthModel>{
    AuthModel findByCookieValue(String cookieValue);
    boolean checkIsAdminByCookieValue(String cookieValue);
    void setCookiesForUser(UserModel userModel, String cookieValue);
}
