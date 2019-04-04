package teodorlazarov.getfood.domain.models.view;

import teodorlazarov.getfood.domain.entities.UserRole;

import java.time.LocalDate;
import java.util.Set;

public class UserViewModel {

    private String id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate registeredOn;
    private Set<String> roles;
    private String shoppingCart;

    public UserViewModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getRegisteredOn() {
        return this.registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getShoppingCart() {
        return this.shoppingCart;
    }

    public void setShoppingCart(String shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}
