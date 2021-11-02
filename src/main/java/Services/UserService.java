package Services;

import forms.SignInUserForm;
import forms.SignUpUserForm;
import models.AuthModel;
import models.UserModel;

import javax.servlet.http.Cookie;

public interface UserService {
    UserModel register(SignUpUserForm signUpUserForm);

    Cookie signIn(SignInUserForm signInUserForm);

    boolean checkIsAdminByCookieValue(Cookie cookie);

    AuthModel getAuthByCookie(Cookie cookie);

    UserModel getUserByCookie(Cookie cookie);
}
