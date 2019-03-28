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
	
	public Car getCar(int carId) {
		return em.find(Car.class, carId);
	}
	
	public Car addCar(Car car) {
		
//		Car newCar = new Car();
//		newCar.setBrand(car.getBrand());
//		newCar.setCountry(car.getCountry());
//		newCar.setRegistration(car.getRegistration());
//		
//		em.persist(newCar);
		
		em.persist(car);

		return car;
	}
	
	public Car updateCar(Car car) {
		
		em.merge(car);
		
		return car;
	
	}
	
	public void deleteCar(int idCar) {
		
		em.remove(em.find(Car.class, idCar));
		
	}

}
