package teodorlazarov.getfood.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserProfileBindingModel {

    private String username;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String oldPassword;
    private String email;
    private String phoneNumber;

    public UserProfileBindingModel() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty(message = "Full name cannot be empty.")
    @Size(min = 3, max = 50, message = "Full name must be between 3 and 50 characters.")
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 8, max = 250, message = "Password must be minimum 8 characters.")
    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @NotEmpty(message = "Email cannot be empty.")
    @Size(min = 4, message = "Email must be minimum 4 characters long.")
    @Pattern(regexp = "^\\w+@\\w+\\..{2,3}(.{2,3})?$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email address is not valid.")
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Phone number cannot be empty.")
    @Size(min = 6, max = 15, message = "Phone number must be between 6 and 15 characters.")
    @Pattern(regexp = "\\+?[0-9]{5,12}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Phone number is not correct.")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
