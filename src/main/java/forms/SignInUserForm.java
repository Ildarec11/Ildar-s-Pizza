package forms;

public class SignInUserForm {
    private String email;
    private String password;

    public SignInUserForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public SignInUserForm() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
