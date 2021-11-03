package services;

import forms.SignInUserForm;
import forms.SignUpUserForm;
import models.AuthModel;
import models.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import repositories.AuthRepository;
import repositories.UserRepository;

import javax.servlet.http.Cookie;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private UserRepository usersRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthRepository authRepository;

    public UserServiceImpl(UserRepository usersRepository, AuthRepository authRepository) {
        this.usersRepository = usersRepository;
        this.authRepository = authRepository;
    }

    @Override
    public UserModel register(SignUpUserForm signUpUserForm) {

        UserModel userModel = new UserModel();
        userModel.setFullName(signUpUserForm.getFullName());
        userModel.setEmail(signUpUserForm.getEmail());

        String passwordHash = passwordEncoder.encode(signUpUserForm.getPassword());

        userModel.setPassword(passwordHash);

        return usersRepository.save(userModel);
    }
    @Override
    public Cookie signIn(SignInUserForm signInUserForm) {

        UserModel user = usersRepository.findByLogin(signInUserForm.getEmail());

        if (user != null) {
            if (passwordEncoder.matches(signInUserForm.getPassword(), user.getPassword())) {
                System.out.println("Вход выполнен");
                String cookieValue = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("auth", cookieValue);
                cookie.setMaxAge(10 * 60 * 60 * 60);
                authRepository.setCookiesForUser(user, cookie.getValue());
                return cookie;
            } else {
                System.out.println("Вход не выполнен");
            }
        }
        return null;
    }

    @Override
    public boolean checkIsAdminByCookieValue(String cookieValue) {
        return authRepository.checkIsAdminByCookieValue(cookieValue);
    }

    @Override
    public AuthModel getAuthByCookie(String cookieValue) {
        return authRepository.findByCookieValue(cookieValue);
    }

    @Override
    public UserModel getUserByCookie(String cookieValue) {
        return authRepository.findByCookieValue(cookieValue).getUserModel();
    }
}
