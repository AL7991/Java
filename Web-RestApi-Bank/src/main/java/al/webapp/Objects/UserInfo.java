package al.webapp.Objects;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "User_information")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;

    private String street;

    private String state;

    private String zip;

    public Long getId() {
        return id;
    }

    public UserInfo(){
    }

    public UserInfo(Long id, String name, String street, String state, String zip) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.state = state;
        this.zip = zip;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(name, userInfo.name) && Objects.equals(street, userInfo.street) && Objects.equals(state, userInfo.state) && Objects.equals(zip, userInfo.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, street, state, zip);
    }
}
