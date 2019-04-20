package teodorlazarov.getfood.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddressCreateBindingModel {

    private String id;
    private String name;
    private String city;
    private String address;
    private String phoneNumber;
    private String notes;

    public AddressCreateBindingModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty(message = "Address name cannot be empty.")
    @Size(min = 3, max = 10, message = "Address name must be between 3 and 10 characters.")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "City cannot be empty.")
    @Size(min = 3, max = 30, message = "City name must be between 3 and 30 characters.")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = "Delivery address cannot be empty.")
    @Size(min = 3, max = 50, message = "Delivery address must be between 3 and 50 characters.")
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotEmpty(message = "Phone number cannot be empty.")
    @Size(min = 5, max = 15, message = "Phone number must be between 5 and 15 characters.")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Size(max = 120, message = "Address notes must not be longer than 120 characters.")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
