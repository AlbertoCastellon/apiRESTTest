package acastemi.cars.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Country
 *
 */
@Entity
public class Country {

	   
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", nullable=false, length=50)
	@Size(min=3, max=30, message="The size must be between 3 and 50 characters")
	@NotNull(message="Country may not be empty.")
	private String name;

	public Country() {
		super();
	}   
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}   
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}
	
   
}
