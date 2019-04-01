package acastemi.cars.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 
 * Entity Car class : Lightweight persistence domain object. Represents a table in 
 * a relational database, and each entity instance corresponds to a row in that table. 
 * The persistent state of an entity is represented through either persistent fields or persistent 
 * properties. These fields or properties use object/relational mapping annotations to map the entities
 *  and entity relationships to the relational data in the underlying data store.
 *
 */
@Entity
@Table
public class Car {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "brand", nullable=false, length=50)
	@NotNull(message="Brand cannot be empty.")
	@Size(min=3, max=50, message="The size must be between 3 and 50 characters")
	private String brand;
	
	@Column(name = "registration", nullable=false)
	@NotNull(message="Registration cannot be empty.")
	private Timestamp registration;
	
	@Column(name = "country", nullable=false, length=50)
	@Size(min=3, max=30, message="The size must be between 3 and 50 characters")
	@NotNull(message="Country may not be empty.")
	private String country;
	
	@Column(name = "createdAt", updatable=false)
	private Date createdAt;
	
	@Column(name = "lastUpdated")
	private Date lastUpdated;
	
	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
		lastUpdated = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		lastUpdated = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Car [id= " + id + ", brand= " + brand + ", registration= " + registration + ", country= " + country
				+ ", createdAt= " + createdAt + ", lastUpdated= " + lastUpdated + "]";
	}
	
	
	
}