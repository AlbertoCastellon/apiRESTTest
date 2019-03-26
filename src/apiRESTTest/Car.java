package apiRESTTest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table
public class Car {

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id")
	private UUID id;
	@Column(name = "brand")
	private String brand;
	@Column(name = "registration")
	private Timestamp registration;
	@Column(name = "country")
	private String country;
	@Column(name = "createdAt")
	private Timestamp createdAt;
	@Column(name = "lastUpdated")
	private Timestamp lastUpdated;

	public Car() {
		
	}

	public Car(String brand, Timestamp registration, String country, Timestamp createdAt, Timestamp lastUpdated) {
		super();
		this.brand = brand;
		this.registration = registration;
		this.country = country;
		this.createdAt = createdAt;
		this.lastUpdated = lastUpdated;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", brand=" + brand + ", registration=" + registration + ", country=" + country
				+ ", createdAt=" + createdAt + ", lastUpdated=" + lastUpdated + "]";
	}
	
	
	
	
}
