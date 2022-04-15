package burger;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import burger.Order;



@Entity
@Table(name="Burger_Order")
public class Order implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date placedAt;

	@NotBlank(message="* Field required")
	@Size(max = 50 , message = "The name is too long.")
	private String name;
	@NotBlank(message="* Field required")
	@Size(max = 50 , message = "The street name is too long.")
	private String street;
	@NotBlank(message="* Field required")
	@Size(max = 50 , message = "The street name is too long.")
	private String city;
	@NotBlank(message="* Field required")
	@Size(max = 50 , message = "The state name is too long")
	private String state;
	@NotBlank(message="* Field required")
	@Size(max = 20 , message = "Invalid number.")
	private String zip;
	@CreditCardNumber(message="* Wrong credit card number")
	private String ccNumber;
	@Pattern(regexp ="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$" , message="* Wrong date format, MM / YY")
	private String ccExpiration;
	@Digits(integer=3 , fraction=0 , message="Incorrect code CVV.")
	private String ccCVV;
	@ManyToOne(targetEntity = User.class)
	private User user;
	
	@ManyToMany(targetEntity = Burger.class)
	private List<Burger> burgers = new ArrayList<Burger>();
	
	public void addDesign(Burger design) {
		this.burgers.add(design);
	}
	
	@PrePersist
	void PlacedAt() {
		this.placedAt = new Date();
	}
	
//							CONSTRUCTOR
	
	public Order() {
	}
	
//							GETTERS & SETTERS
	
	public Long getId() {
		return id;
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
	public String getCcNumber() {
		return ccNumber;
	}
	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}
	public String getCcExpiration() {
		return ccExpiration;
	}
	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}
	public String getCcCVV() {
		return ccCVV;
	}
	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getPlacedAt() {
		return placedAt;
	}
	public void setPlacedAt(Date placedAt) {
		this.placedAt = placedAt;
	}
	public List<Burger> getBurgers() {
		return burgers;
	}
	public void setBurgers(List<Burger> burgers) {
		this.burgers = burgers;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
//							HASHCODE & EQUALS
	
	@Override
	public int hashCode() {
		return Objects.hash(ccCVV, ccExpiration, ccNumber, city, id, name, placedAt, state, street, burgers, user, zip);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(ccCVV, other.ccCVV) && Objects.equals(ccExpiration, other.ccExpiration)
				&& Objects.equals(ccNumber, other.ccNumber) && Objects.equals(city, other.city)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(placedAt, other.placedAt) && Objects.equals(state, other.state)
				&& Objects.equals(street, other.street) && Objects.equals(burgers, other.burgers)
				&& Objects.equals(user, other.user) && Objects.equals(zip, other.zip);
	}
	
}
