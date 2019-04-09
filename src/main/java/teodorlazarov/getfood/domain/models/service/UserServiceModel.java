package teodorlazarov.getfood.domain.models.service;

import teodorlazarov.getfood.domain.entities.ShoppingCart;
import teodorlazarov.getfood.domain.entities.UserRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UserServiceModel {

    private String id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDate registeredOn;
    private Set<UserRole> roles;
    private ShoppingCartServiceModel shoppingCart;
    private List<AddressServiceModel> addresses;

    public UserServiceModel() {
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<UserRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public ShoppingCartServiceModel getShoppingCart() {
        return this.shoppingCart;
    }

    public void setShoppingCart(ShoppingCartServiceModel shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<AddressServiceModel> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<AddressServiceModel> addresses) {
        this.addresses = addresses;
    }
}
