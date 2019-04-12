package acastemi.cars.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@JoinColumn(name = "brand", nullable=false)
	@NotNull(message="Brand cannot be empty.")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Brand brand;
	
	@Column(name = "registration", nullable=false)
	@NotNull(message="Registration cannot be empty.")
	private Timestamp registration;
	
	@JoinColumn(name = "country", nullable=false)
	@NotNull(message="Country may not be empty.")
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Country country;
	
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Timestamp getRegistration() {
		return registration;
	}

	public void setRegistration(Timestamp registration) {
		this.registration = registration;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
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
	
	public String toString2() {
		return "Car [brand= " + brand + ", registration= " + registration + ", country= " + country + "]";
	}
	
	
	
}