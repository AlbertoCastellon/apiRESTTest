package acastemi.cars.rest;


import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
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


@Path("/cars")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	@EJB
	private CarService carSvc;

	@GET
	public Response findAllCars() {
		return Response.ok(carSvc.getCars(), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("{carId}")
	public Response findCar(@PathParam("carId") final int carId) {

		final Car car = carSvc.getCar(carId);
		
		if(car==null)
			return Response.status(404).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
			.build();
		else 
			return Response.ok(car, MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	public Response createCar(final Car carRequest) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) 
			return Response.status(400).entity(validationsErrors)
					.build();
		
		return Response.ok(carSvc.createCar(carRequest)).build();

	}
	
	@PUT
	@Path("{carId}")
	public Response updateCar(final Car carRequest, @PathParam("carId") final int carId) {
		
		final ArrayList<String> validationsErrors = ValidatorUtil.validate(carRequest);
		
		if(!validationsErrors.isEmpty()) 
			return Response.status(400).entity(validationsErrors)
					.build();
		
		final Car car = carSvc.updateCar(carRequest, carId);
		
		if(car==null)
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
			.build();
		else 
			return Response.ok(car, MediaType.APPLICATION_JSON).build();
		
		
	}

	@DELETE
	@Path("{carId}")
	public Response deleteCar(@PathParam("carId") final int carId) {
		
		if(carSvc.deleteCar(carId))
			return Response.noContent().build();
		else
			return Response.status(400).entity("{\"error\": \"The car with the id " + carId + " does not exist.\"}")
				.build();
		
	}

	@HEAD
	public Response pingUsersService() {
		return Response.noContent().header("running", true).build();
	}

}