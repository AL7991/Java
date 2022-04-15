package burger.security;


import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import burger.User;

@Component
public class RegistrationForm {
@NotBlank
private String username;
@NotBlank
private String password;
@NotBlank
private String name;
@NotBlank
private String street;
@NotBlank
private String city;
@NotBlank
private String state;
@NotBlank
private String zip;
@NotBlank
private String phone;

// 						CONSTRUCTOR

public RegistrationForm() {
	
}

// 						GETTERS & SETTERS

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getName() {
	return name;
}
public void setName(String fullname) {
	this.name = fullname;
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
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}

// 						OTHER METHOD

public User toUser(PasswordEncoder passwordEncoder) {
	return new User(
			username, passwordEncoder.encode(password), 
			name, street, city, state, zip, phone);
}

// 						HASHCODE & EQUALS

@Override
public int hashCode() {
	return Objects.hash(city, name, password, phone, state, street, username, zip);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	RegistrationForm other = (RegistrationForm) obj;
	return Objects.equals(city, other.city) && Objects.equals(name, other.name)
			&& Objects.equals(password, other.password) && Objects.equals(phone, other.phone)
			&& Objects.equals(state, other.state) && Objects.equals(street, other.street)
			&& Objects.equals(username, other.username) && Objects.equals(zip, other.zip);
}




}
