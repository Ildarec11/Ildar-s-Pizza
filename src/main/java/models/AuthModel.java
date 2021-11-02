package models;

public class AuthModel {

    private int id;
    private UserModel userModel;
    private String cookieValue;

    public int getId() {
        return id;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public String getCoolieValue() {
        return cookieValue;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public void setCookieValue(String coolieValue) {
        this.cookieValue = coolieValue;
    }
}
