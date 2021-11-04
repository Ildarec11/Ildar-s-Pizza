package services;

import forms.SignInUserForm;
import forms.SignUpUserForm;
import models.AuthModel;
import models.DiscountModel;
import models.DishModel;
import models.UserModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import repositories.AuthRepository;
import repositories.DishRepository;
import repositories.UserRepository;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private UserRepository usersRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthRepository authRepository;
    private DishRepository dishRepository;

    public UserServiceImpl(UserRepository usersRepository, AuthRepository authRepository, DishRepository dishRepository) {
        this.usersRepository = usersRepository;
        this.authRepository = authRepository;
        this.dishRepository = dishRepository;
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

    @Override
    public void buyDish(UserModel userModel, DishModel dishModel) {
        Optional<DiscountModel> discountModel = dishRepository.getMaxDiscountByDishId(dishModel.getId());
        BigDecimal dishCost = dishModel.getCost();
        int percentageInt = 1;
        if (discountModel.isPresent()) {
            BigDecimal percentage = BigDecimal.valueOf((long) discountModel.get().getPercentage());
            dishCost = dishCost.multiply(percentage);
            percentageInt = discountModel.get().getPercentage();
        }
        usersRepository.spendMoney(userModel, dishCost);
        dishRepository.writeDishToPurchaseHistory(userModel, dishModel, dishCost, percentageInt);
    }
}
