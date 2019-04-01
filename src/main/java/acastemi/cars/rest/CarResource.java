package acastemi.cars.rest;


import java.util.ArrayList;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;
import acastemi.cars.util.ValidatorUtil;

/**
 * The Java class will be hosted at the URI path "/cars"
 * The @Consumes annotation is used to specify which MIME media 
 * types of representations a resource can accept, or consume, 
 * from the client
 *
 */
@Path("/cars")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	/**
	 * Initializes the Enterprise Java Bean
	 */
	@EJB
	private CarService carSvc;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	/**
	 * The Java method will process HTTP GET request and call {@link acastemi.cars.control.CarService#getCars() getCars()}
	 * @return Response object containing an array of Car objects
	 */
	@GET
	public Response findAllCars() {
		return Response.ok(carSvc.getCars(), MediaType.APPLICATION_JSON).build();
	}

	

	/**
	 * The Java method will process HTTP GET request
	 * @param carId the id from the Car object that we want to get from the database
	 * @return Response object containing a single Car object, or an error if the Car object doesn't exists
	 */
	@GET
	@Path("{carId}")
	public Response findCar(@PathParam("carId") final int carId) {

		final Car car = carSvc.getCar(carId);
		
		if(car==null) {
			
			LOGGER.info("Failed to find the car with the id " + carId + " in the database.");
			return Response.status(404).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
			.build();
			
		}else {
			
			LOGGER.info("The car " + car + " was found in the database.");
			
			return Response.ok(car, MediaType.APPLICATION_JSON).build();
			
		}

	}
	
	/**
	 * The Java method will process HTTP POST request
	 * @param carRequest Car object that we want to insert in the database
	 * @return the Car Object successfully created 
	 */
	@POST
	public Response createCar(final Car carRequest) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) { 
			
			for(String error : validationsErrors)
				LOGGER.info(error);
			
			return Response.status(400).entity(validationsErrors)
					.build();
			
		}
		return Response.ok(carSvc.createCar(carRequest)).build();

	}
	
	/**
	 * The Java method will process HTTP PUT request
	 * @param carRequest Car object that we want to modify from the database, with the new data
	 * @param carId the id of the Car object that we want to update
	 * @return the Car Object successfully created 
	 */
	@PUT
	@Path("{carId}")
	public Response updateCar(final Car carRequest, @PathParam("carId") final int carId) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) {
			
			for(String error : validationsErrors)
				LOGGER.info(error);
			
			return Response.status(400).entity(validationsErrors)
					.build();
		}
		
		final Car car = carSvc.updateCar(carRequest, carId);
		
		if(car==null)
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
			.build();
		else 
			return Response.ok(car, MediaType.APPLICATION_JSON).build();
		
		
	}

	/**
	 * The Java method will process HTTP DELETE request
	 * @param carId the id from the Car object that we want to delete from the database
	 * @return Response object containing a nothing, if the Car object gets successfully deleted, or an 
	 * error if the Car object doesn't exists
	 */
	@DELETE
	@Path("{carId}")
	public Response deleteCar(@PathParam("carId") final int carId) {
		
		if(carSvc.deleteCar(carId))
			return Response.noContent().build();
		else
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
				.build();
		
	}

}