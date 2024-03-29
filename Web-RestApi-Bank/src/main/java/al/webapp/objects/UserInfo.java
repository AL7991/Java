package al.webapp.objects;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "User_information")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = "Providing your name is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String name;
    @NotBlank(message="Providing street is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String street;
    @NotBlank(message="Providing city is mandatory.")
    @Size(max = 50 , message = "Name too long.")
    private String city;
    @NotBlank(message="Providing zip code is mandatory.")
    @Pattern(regexp ="^[0-9][0-9][-]?[0-9][0-9][0-9]$" , message="Invalid zip code.")
    private String zip;
    @Digits(integer=9 , fraction=0 , message="Invalid phone number.")
    private String phone;

    public Long getId() {
        return id;
    }

    public UserInfo(){
    }

    public UserInfo(Long id, String name, String street, String city, String zip, String phone) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(name, userInfo.name) && Objects.equals(street, userInfo.street) && Objects.equals(city, userInfo.city) && Objects.equals(zip, userInfo.zip) && Objects.equals(phone, userInfo.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, street, city, zip, phone);
    }
}
