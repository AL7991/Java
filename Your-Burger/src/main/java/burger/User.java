package burger;


import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import burger.User;

@Entity
@Table(name = "User")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name ="name" )
	private String name;
	
	@Column(name ="street" )
	private String street;
	
	@Column(name ="city" )
	private String city;
	
	@Column(name ="state" )
	private String state;
	
	@Column(name ="zip" )
	private String zip;
	
	@Column(name ="phoneNumber" )
	private String phoneNumber;

	@Column(name ="username" )
	private String username;
	
	@Column(name ="password" )
	private String password;
	
//							CONSTRUCTORS
	
	public User() {
	}
	
	public User(String username, String password, String fullname, String street, String city, String state,
			String zip, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.name = fullname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
	}
	
//							GETTERS & SETTERS
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}


//							OTHER METHODS
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
		
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


//						HASHCODE & EQUALS
	
	@Override
	public int hashCode() {
		return Objects.hash(city, name, id, password, phoneNumber, state, street, username, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(city, other.city) && Objects.equals(name, other.name) && id == other.id
				&& Objects.equals(password, other.password) && Objects.equals(phoneNumber, other.phoneNumber)
				&& Objects.equals(state, other.state) && Objects.equals(street, other.street)
				&& Objects.equals(username, other.username) && Objects.equals(zip, other.zip);
	}

	
}

