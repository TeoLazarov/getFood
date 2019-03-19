package teodorlazarov.getfood.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private LocalDate registeredOn;
//    private Set<UserRole> roles;
//    private List<Address> addresses;
//    private List<Order> orders;

    public User() {
    }

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "registered_on", nullable = false, updatable = false)
    public LocalDate getRegisteredOn() {
        return this.registeredOn;
    }

    public void setRegisteredOn(LocalDate registeredOn) {
        this.registeredOn = registeredOn;
    }

//    public List<Address> getAddresses() {
//        return this.addresses;
//    }
//
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    public List<Order> getOrders() {
//        return this.orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
}
