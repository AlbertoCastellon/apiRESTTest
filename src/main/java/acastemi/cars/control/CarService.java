package acastemi.cars.control;

import java.util.List;
import java.util.logging.Logger;

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
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * The entity manager object that it's injected by the container and created 
	 * by the Persistence Unit JPA_PU.
	 * All the transactions will be managed automatically.
	 */
	@PersistenceContext(unitName = "em_payara")
	private EntityManager em;
	/**
	 * Creates a query to retrieve all the Car objects from the database
	 * @return List of Car objects, empty array if there is none
	 */
	@SuppressWarnings("unchecked")
	public List<Car> getCars() {
		
		LOGGER.info("Getting all the cars from the database");
		
		return (List<Car>) em.createQuery("SELECT c FROM Car c").getResultList();
	}

	
	/**
	 * Looks for a Car on the Database.
	 * 
	 * @param carId The id of the car that needs to be updated
	 * @return the found Car object
	 */
	public Car getCar(final int carId) {

		LOGGER.info("Getting a car from the database");
		
		return em.find(Car.class, carId);
	}
	
	/**
	 * 
	 * @param car The object that needs to be inserted in the database
	 * @return The object inserted in the database
	 * 
	 */
	public Car createCar(final Car car) {

		LOGGER.info("Creating the car " + car + "in the database");
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
			LOGGER.info("Updating a car in the database");
			em.merge(updatedCar);
			return updatedCar;
		}
		else {
			LOGGER.info("Failed to find the car with the id " + carId + " in the database.");
			return null;
		}

	}

	/**
	 * 
	 * @param carId The id of the car that needs to be deleted
	 * @return a boolean that tells if the delete operation was successful 
	 * 
	 */
	public boolean deleteCar(final int carId) {
		
		Car car = em.find(Car.class, carId);
		
		if(car!=null) {
			LOGGER.info("Deleting the car: " + car + " from the database");
			em.remove(car);
			return true;
		}
		else {
			LOGGER.info("Failed to find the car with the id " + carId + " in the database.");
			return false;
		}
		
	}

}
