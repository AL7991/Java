package burger;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Ingredient {
	
	@Id
	private String id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type typ;
	
	public static enum Type {
		WRAP , PROTEIN , VEGGIES , CHEESE , SAUCE
	}
	
	
//							CONSTRUCTORS
	
	public Ingredient() {
	}

	public Ingredient(String id, String name, Type typ) {
		this.id = id;
		this.name = name;
		this.typ = typ;
	}
	
//							GETTERS & SETTERS

	public Type getType() {
		return this.typ;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Type getTyp() {
		return typ;
	}


	public void setTyp(Type typ) {
		this.typ = typ;
	}

//							HASHCODE & EQUALS

	@Override
	public int hashCode() {
		return Objects.hash(id, name, typ);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingredient other = (Ingredient) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name) && typ == other.typ;
	}

}
