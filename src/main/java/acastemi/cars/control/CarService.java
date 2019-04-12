package acastemi.cars.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import acastemi.cars.entity.Car;

/**
 * This class contains the CRUD methods necessary to interact with the database.
 *
 */
@Stateless
public class CarService {
	
    private final static Logger LOGGER = Logger.getLogger(CarService.class);
	
	@EJB
	PersistenceService persistenceService = new PersistenceService();
	
	/**
	 * Creates a query to retrieve all the Car objects from the database
	 * @return List of Car objects, empty array if there is none
	 */
	@SuppressWarnings("unchecked")
	public List<Car> getAll() {
		
		LOGGER.info("Getting all the cars from the database");
		
		return persistenceService.findAll(Car.class);
	}

	
	/**
	 * Looks for a Car on the Database.
	 * 
	 * @param carId The id of the car that needs to be updated
	 * @return the found Car object
	 */
	
	public Car get(final int id) {

		LOGGER.info("Getting a car from the database");
		
		return persistenceService.find(Car.class, id);
	}
	
	/**
	 * 
	 * @param car The object that needs to be inserted in the database
	 * @return The object inserted in the database
	 * 
	 */
	public Car create(final Car car) {

		LOGGER.info("Creating the car "+  car.toString2() +" in the database");
		persistenceService.create(car);

		return car;
	}

	/**
	 * 
	 * @param updatedCar The car object that needs to be updated with the new values
	 * @param carId The id of the car that needs to be updated
	 * @return The updated Car object from the database
	 * 
	 */
	public Car update(final Car updatedCar, int carId) {
		
		updatedCar.setId(carId);
		
		if(persistenceService.find(Car.class, carId) != null) {
			LOGGER.info("Updating a car in the database");
			persistenceService.update(updatedCar);
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
	public boolean delete(final int carId) {
		
		Car car = persistenceService.find(Car.class, carId);
		
		if(car!=null) {
			LOGGER.info("Deleting the car: " + car + " from the database");
			persistenceService.delete(car);
			return true;
		}
		else {
			LOGGER.info("Failed to find the car with the id " + carId + " in the database.");
			return false;
		}
		
	}


}
