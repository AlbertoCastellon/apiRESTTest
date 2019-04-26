package acastemi.cars.rest;


import java.util.ArrayList;


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

import org.apache.log4j.Logger;

import acastemi.cars.control.CarService;
import acastemi.cars.control.PersistenceService;
import acastemi.cars.control.PersistenceService.EntityNotFoundException;
import acastemi.cars.entity.Car;
import acastemi.cars.util.ValidatorUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * The Java class will be hosted at the URI path "/cars"
 * The @Consumes annotation is used to specify which MIME media 
 * types of representations a resource can accept, or consume, 
 * from the client
 *
 */
@Path("/cars")
@Api(value="cars", consumes="application/json", produces="application/json")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	/**
	 * Initializes the Enterprise Java Bean
	 */
	@EJB
	private CarService carSvc;
	
	private final static Logger LOGGER = Logger.getLogger(CarService.class);

	
	/**
	 * The Java method will process HTTP GET request and call {@link acastemi.cars.control.CarService#getAll() getCars()}
	 * @return Response object containing an array of Car objects
	 */
	@GET
	@ApiOperation(value = "Retrieve all the cars in the system",
	    response = Car.class,
	    responseContainer = "List")
	public Response findAllCars() {
		return Response.ok(carSvc.getAll(), MediaType.APPLICATION_JSON).build();
	}

	

	/**
	 * The Java method will process HTTP GET request
	 * @param carId the id from the Car object that we want to get from the database
	 * @return Response object containing a single Car object, or an error if the Car object doesn't exists
	 */
	@GET
	@ApiOperation(value = "Retrieve a car from the system",
    response = Car.class)
	@ApiResponses(value = { 
		      @ApiResponse(code = 400, message = "Validation error", 
		                    response = Boolean.class),
		      @ApiResponse(code = 404, message = "{\"error\": \"The car with the id {carId} does not exist.\"}") })
	@Path("{carId}")
	public Response findCar(@ApiParam(value = "id of the car that needs to be retrieved", required = true) @PathParam("carId") final int carId) {

		Car car;
		try {
			
			car = (Car) carSvc.get(carId);
			LOGGER.info("The car " + car + " was found in the database.");
			
			return Response.ok(car, MediaType.APPLICATION_JSON).build();
			
		} catch (EntityNotFoundException e) {
			
			LOGGER.info("Failed to find the car with the id " + carId + " in the database.");
			return Response.status(404).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
			.build();
			
		}
		

	}
	
	/**
	 * The Java method will process HTTP POST request
	 * @param carRequest Car object that we want to insert in the database
	 * @return the Car Object successfully created 
	 */
	@POST
	@ApiOperation(value = "Creates a new car in the system",
    response = Car.class)
	@ApiResponses(value = { 
		      @ApiResponse(code = 400, message = "Validation error", 
		                    response = Boolean.class),
		      @ApiResponse(code = 404, message = "{\"error\": \"The car with the id {carId} does not exist.\"}") })
	public Response createCar(final Car carRequest) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) { 
			
			for(String error : validationsErrors)
				LOGGER.info(error);
			
			return Response.status(400).entity(validationsErrors)
					.build();
			
		}
		return Response.accepted((carSvc.create(carRequest))).build();

	}
	
	/**
	 * The Java method will process HTTP PUT request
	 * @param carRequest Car object that we want to modify from the database, with the new data
	 * @param carId the id of the Car object that we want to update
	 * @return the Car Object successfully created 
	 */
	@PUT
	@ApiOperation(value = "Modifies a car in the system",
    response = Car.class)
	@ApiResponses(value = { 
		      @ApiResponse(code = 400, message = "Validation error", 
		                    response = Boolean.class),
		      @ApiResponse(code = 404, message = "{\"error\": \"The car with the id {carId} does not exist.\"}") })
	@Path("{carId}")
	public Response updateCar(final Car carRequest, @ApiParam(value = "id of the car that needs to be modified", required = true) @PathParam("carId") final int carId) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) {
			
			for(String error : validationsErrors)
				LOGGER.info(error);
			
			return Response.status(400).entity(validationsErrors)
					.build();
		}
		
		Car car;
		try {
			car = carSvc.update(carRequest, carId);
			return Response.ok(car, MediaType.APPLICATION_JSON).build();
		} catch (EntityNotFoundException e) {
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
					.build();
		}
		
		
	}

	/**
	 * The Java method will process HTTP DELETE request
	 * @param carId the id from the Car object that we want to delete from the database
	 * @return Response object containing a nothing, if the Car object gets successfully deleted, or an 
	 * error if the Car object doesn't exists
	 */
	@DELETE
	@ApiOperation(value = "Deletes a car in the system")
	@ApiResponses(value = { 
		      @ApiResponse(code = 400, message = "Validation error", 
		                    response = Boolean.class),
		      @ApiResponse(code = 404, message = "{\"error\": \"The car with the id {carId} does not exist.\"}") })
	@Path("{carId}")
	public Response deleteCar(@ApiParam(value = "id of the car that needs to be deleted", required = true)  @PathParam("carId") final int carId) {
		
		try {
			
			carSvc.delete(carId);
			
			return Response.noContent().build();
			
		}catch (EntityNotFoundException e) {			
			
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
					.build();	
			
		}
					
		
	}

}