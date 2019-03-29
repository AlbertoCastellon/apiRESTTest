package acastemi.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import acastemi.cars.entity.Car;

/**
 * This class contains the CRUD methods necessary to interact with the database.
 *
 */
@Stateless
public class CarService {
	
	/**
	 * The entity manager object that it's injected by the container and created 
	 * by the Persistence Unit JPA_PU.
	 * All the transactions will be managed automatically.
	 */
	@PersistenceContext(unitName = "em_payara")
	private EntityManager em;
	/**
	 * Creates a query to retrieve all the Car objects from the database
	 * @return List of Car objects
	 */
	@SuppressWarnings("unchecked")
	public List<Car> getCars() {
		return (List<Car>) em.createQuery("SELECT c FROM Car c").getResultList();
	}

	
	/**
	 * Looks for a Car on the Database.
	 * 
	 * @param carId The id of the car that needs to be updated
	 * @return the found Car object
	 */
	public Car getCar(final int carId) {
		return em.find(Car.class, carId);
	}
	
	/**
	 * 
	 * @param car The object that needs to be inserted in the database
	 * @return The object inserted in the database
	 * 
	 */
	public Car createCar(final Car car) {

		em.persist(car);

		return car;
	}

	/**
	 * 
	 * @param updatedCar The car object that needs to be updated with the new values
	 * @param carId The id of the car that needs to be updated
	 * @return The updated Car object from the database
	 * 
	 */
	public Car updateCar(final Car updatedCar, int carId) {
		
		updatedCar.setId(carId);
		
		if(em.find(Car.class, carId) != null) {
			em.merge(updatedCar);
			return updatedCar;
		}
		else
			return null;
		

	}

	/**
	 * 
	 * @param carId The id of the car that needs to be deleted
	 * @return a boolean that tells if the delete operation was successful 
	 */
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
