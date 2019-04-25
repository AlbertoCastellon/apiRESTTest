package acastemi.cars.entity;

import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Brand
 *
 */
@Entity
public class Brand {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "brand", nullable=false, length=50)
	@NotNull(message="Brand cannot be empty.")
	@Size(min=3, max=50, message="The size must be between 3 and 50 characters")
	private String name;

	public Brand() {
		super();
	}   
	
	public Brand(int id, String name) {
		super();
		this.id = id;
		this.name = name;
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
		return "Brand [id=" + id + ", name=" + name + "]";
	}
   
}
