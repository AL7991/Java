package burger;


import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Burger{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Date createdAt;
	
	@Size(min= 5 , message = " * The name is too short." )
	@Size(max = 50 , message = " * The name is too long.")
	private String burgerName;
	
	@NotNull(message = " * At least one ingredient.")
	@ManyToMany(targetEntity = Ingredient.class)
	@Size(min= 1 , message = " * At least one ingredient.")
	private List<Ingredient> ingredients;
	
	@PrePersist
	public void createdAt() {
		this.createdAt = new Date();
	}

//							CONSTRUCTORS
	
	public Burger() {
	}

	public Burger(Long id, Date createdAt, String burgerName, List<Ingredient> ingredients) {
		this.id = id;
		this.createdAt = createdAt;
		this.burgerName = burgerName;
		this.ingredients = ingredients;
	}	
	
//							GETTERS & SETTERS
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getBurgerName() {
		return burgerName;
	}

	public void setBurgerName(String burgerName) {
		this.burgerName = burgerName;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

//							HASHCODE & EQUALS
	
	@Override
	public int hashCode() {
		return Objects.hash(burgerName, createdAt, id, ingredients);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Burger other = (Burger) obj;
		return Objects.equals(burgerName, other.burgerName) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(id, other.id) && Objects.equals(ingredients, other.ingredients);
	}
	
	
	
	
	
}
