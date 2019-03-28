package acastemi.cars.rest;


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

import com.google.gson.Gson;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;




@Path("/cars")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	@EJB
	private CarService carSvc;

	@GET
	public Response findAllCars() {
		return Response.ok(new Gson().toJson(carSvc.getCars()), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("{carId}")
	public Response findCar(@PathParam("carId") int carId) {

		return Response.ok(carSvc.getCar(carId), MediaType.APPLICATION_JSON).build();

	}
	
	@POST
	public Response createCar(Car carRequest) {
		
		return Response.ok(carSvc.addCar(carRequest)).build();

	}
	
	@PUT
	public Response updateCar(Car carRequest) {
		
		return Response.ok(carSvc.updateCar(carRequest)).build();
		
	}

	@DELETE
	@Path("{carId}")
	public Response deleteCar(@PathParam("carId") int carId) {
		
		carSvc.deleteCar(carId);
		
		return Response.noContent().build();
		
	}

	@HEAD
	public Response pingUsersService() {
		return Response.noContent().header("running", true).build();
	}

}