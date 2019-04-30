package acastemi.cars.timer;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.apache.log4j.Logger;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;


/**
 * A Singleton class created and initialized at the start of the application.
 * 
 * Check the cars from the db every 2 minutes and changes the field "checked" to true if it was false.
 */
@Singleton
public class CheckCarsTimer {

	@EJB
	protected CarService carSvc;

	private static final Logger LOGGER = Logger.getLogger(CheckCarsTimer.class);

	@Schedule(minute = "*/2", hour = "*")
	public void checkCarsPeriodically() {

		List<Car> carList = carSvc.getAllNotChecked();

		LOGGER.info("Checking cars...");

		for (Car car : carList) {

			car.setChecked(true);
			try {

				Car updatedCar = carSvc.update(car, car.getId());
				LOGGER.info("Car with the id " + updatedCar.getId() + " checked succesfully.");

			} catch (EntityNotFoundException e) {

				LOGGER.error("The car does not exist");

			}

		}
	}
}
