package acastemi.cars.control;


import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import acastemi.cars.entity.Car;


@Stateless
public class CarService {
	@PersistenceContext(unitName="em_payara")
	private EntityManager em;


	public List<Car> getCars() {
		return (List<Car>) em.createQuery("SELECT c FROM Car c").getResultList();
	}
	
	public Car getCar(final int carId) {
		return em.find(Car.class, carId);
	}
	
	public Car createCar(final Car car) {
		
		Car car1 = new Car();
		car1.setBrand(car.getBrand());
		car1.setCountry(car.getCountry());
		car1.setRegistration(car.getRegistration());
		
		em.persist(car1);

		return car;
	}
	
	public Car updateCar(final Car car) {
		
		em.merge(car);
		
		return car;
	
	}
	
	public void deleteCar(final int carId) {
		
		em.remove(em.find(Car.class, carId));
		
	}

}
