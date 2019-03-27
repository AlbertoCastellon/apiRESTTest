package apiRESTTest.rest;


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

import apiRESTTest.control.CarService;
import apiRESTTest.entity.Car;


@Path("/cars")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	private CarService carSvc = new CarService();

	@GET
	public Response findAllCars() {
		
		return carSvc.getAllCars();
	}

	@GET
	@Path("{carId}")
	public Response findCar(@PathParam("carId") String carId) {

		return carSvc.getCarById(carId);

	}

	@POST
	public Response createCar(Car carRequest) {
		
		return carSvc.addCar(carRequest);

	}

	@PUT
	public Response updateCar(Car carRequest) {
		
		return carSvc.updateCar(carRequest);
		
	}

	@DELETE
	@Path("{carId}")
	public Response deleteCar(@PathParam("carId") String carId) {
		
		return carSvc.deleteCar(carId);
		
	}

	@HEAD
	public Response pingUsersService() {
		return Response.noContent().header("running", true).build();
	}
	



}