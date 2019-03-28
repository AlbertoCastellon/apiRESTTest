package acastemi.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import acastemi.cars.entity.Car;

@Stateless
public class CarService {
	@PersistenceContext(unitName = "em_payara")
	private EntityManager em;

	public List<Car> getCars() {
		return (List<Car>) em.createQuery("SELECT c FROM Car c").getResultList();
	}

	public Car getCar(final int carId) {
		return em.find(Car.class, carId);
	}

	public Car createCar(final Car car) {

		em.persist(car);

		return car;
	}

	public Car updateCar(final Car updatedCar, int carId) {
		
		updatedCar.setId(carId);
		
		if(em.find(Car.class, carId) != null) {
			em.merge(updatedCar);
			return updatedCar;
		}
		else
			return null;
		

	}

	public boolean deleteCar(final int carId) {
		
		Car car = em.find(Car.class, carId);
		
		if(car!=null) {
			em.remove(car);
			return true;
		}
		else
			return false;
		
	}

}
