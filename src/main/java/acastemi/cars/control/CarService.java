package acastemi.cars.control;

import java.util.List;

import javax.ejb.Stateless;

import acastemi.cars.entity.Car;

/**
 * This class contains the CRUD methods necessary to interact with the database.
 *
 */
@Stateless
public class CarService extends PersistenceService {
	
	/**
	 * Creates a query to retrieve all the Car objects from the database
	 * @return List of Car objects, empty array if there is none
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getAll() {
		
		LOGGER.info("Getting all the cars from the database");
		
		return (List<Object>) em.createQuery("SELECT c FROM Car c").getResultList();
	}

	
	/**
	 * Looks for a Car on the Database.
	 * 
	 * @param carId The id of the car that needs to be updated
	 * @return the found Car object
	 */
	@Override
	public Object get(final int id) {

		LOGGER.info("Getting a car from the database");
		
		return em.find(Car.class, id);
	}
	
	/**
	 * 
	 * @param car The object that needs to be inserted in the database
	 * @return The object inserted in the database
	 * 
	 */
	@Override
	public Object create(final Object car) {

		LOGGER.info("Creating the car "+ ((Car) car).toString2() +" in the database");
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
	@Override
	public Object update(final Object updatedCar, int carId) {
		
		((Car) updatedCar).setId(carId);
		
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
	@Override
	public boolean delete(final int carId) {
		
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
