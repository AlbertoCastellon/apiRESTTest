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

@Entity
@Table
public class Car {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "brand", nullable=false, length=50)
	@NotNull
	@Size(min=3, max=50)
	private String brand;
	@Column(name = "registration", nullable=false)
	private Timestamp registration;
	@Column(name = "country", nullable=false, length=50)
	@Size(min=3, max=30)
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
	
}