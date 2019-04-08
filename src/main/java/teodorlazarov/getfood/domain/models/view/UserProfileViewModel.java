package teodorlazarov.getfood.domain.models.view;

import java.util.List;

public class UserProfileViewModel {

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private List<OrderViewModel> orders;
    private List<AddressViewModel> addresses;

    public UserProfileViewModel() {
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

    public List<OrderViewModel> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderViewModel> orders) {
        this.orders = orders;
    }

    public List<AddressViewModel> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(List<AddressViewModel> addresses) {
        this.addresses = addresses;
    }
}
