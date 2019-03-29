package teodorlazarov.getfood.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity{

    //TODO constraints and validations

    private String name;
    private String city;
    private String address;
    private String phoneNumber;
    private String notes;

    public Address() {
    }

    @NotEmpty(message = "Address name cannot be empty.")
    @Size(max = 30, message = "Address name must not exceed 30 characters.")
    @Column(name = "name", nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = "Address city cannot be empty.")
    @Size(max = 30, message = "Address name must not exceed 30 characters.")
    @Column(name = "city", nullable = false)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty(message = "Address cannot be empty.")
    @Column(name = "address", nullable = false)
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotEmpty(message = "Phone number cannot be empty.")
    @Size(max = 15, message = "Phone number must not exceed 15 characters.")
    @Column(name = "phone_number", nullable = false)
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "notes")
    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
