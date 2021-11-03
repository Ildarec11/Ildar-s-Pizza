package services;

import forms.SignInUserForm;
import forms.SignUpUserForm;
import models.AuthModel;
import models.UserModel;

import javax.servlet.http.Cookie;

public interface UserService {
    UserModel register(SignUpUserForm signUpUserForm);

    Cookie signIn(SignInUserForm signInUserForm);

    boolean checkIsAdminByCookieValue(String cookieValue);

    AuthModel getAuthByCookie(String cookieValue);

    UserModel getUserByCookie(String cookieValue);
}
