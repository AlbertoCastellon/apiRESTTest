package apiRESTTest;

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
	private int id;
	private String brand;
	private Timestamp registration;
	private String country;
	private Timestamp createdAt;
	private Timestamp lastUpdated;

	public Car() {
		
	}

	public Car(int id, String brand, Timestamp registration, String country, Timestamp createdAt, Timestamp lastUpdated) {
		super();
		this.id = id;
		this.brand = brand;
		this.registration = registration;
		this.country = country;
		this.createdAt = createdAt;
		this.lastUpdated = lastUpdated;
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
