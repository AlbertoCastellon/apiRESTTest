package apiRESTTest.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import javax.ws.rs.core.Response.Status;

import apiRESTTest.control.CarService;
import apiRESTTest.entity.Car;


@Path("/cars")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CarResource {

	// User database pre-initialization
	private static final List<Car> cars = new ArrayList<>();
	private CarService carSvc = new CarService();

	@GET
	public Response findAllCars() {
		
		return carSvc.getAllCars();
		//return Response.ok(carListIterator(cars), MediaType.APPLICATION_JSON).build();
	}

	@GET
	@Path("{carId}")
	public Response findCar(@PathParam("carId") int carId) {

		return carSvc.getCarById(carId);
//		// Throws error in case of the car not found
//		if (found.isEmpty())
//			return Response.status(Status.BAD_REQUEST).entity("Car not found").build();
//
//		Car updateCar = found.get(0);
//		return Response.ok(updateCar, MediaType.APPLICATION_JSON).build();

	}

	@POST
	public Response createUser(Car carRequest) {
		
		return carSvc.addCar(carRequest);
//		carRequest.setId(cars.size());
//		cars.add(carRequest);
//		return Response.ok(carRequest).build();
	}

	@PUT
	public Response updateCar(Car carRequest) {
		
		return carSvc.updateCar(carRequest);
//		List<Car> found = cars.stream().filter(x -> carRequest.getId() == x.getId()).collect(Collectors.toList());
//
//		// Throws error in case of the car not found
//		if (found.isEmpty())
//			return Response.status(Status.BAD_REQUEST).entity("Car not found").build();
//
//		Car updateCar = found.get(0);
//		updateCar.setBrand(carRequest.getBrand());
//		updateCar.setCountry(carRequest.getCountry());
//		updateCar.setCreatedAt(new Timestamp(System.currentTimeMillis()));
//		updateCar.setLastUpdated(new Timestamp(System.currentTimeMillis()));
//		updateCar.setRegistration(new Timestamp(System.currentTimeMillis()));
//		return Response.ok(updateCar).build();
	}

	@DELETE
	@Path("{carId}")
	public Response deleteUser(@PathParam("carId") int carId) {
		
		return carSvc.deleteCar(carId);
		
//		System.out.println("carId ==> " + carId);
//		List<Car> found = cars.stream().filter(x -> carId == x.getId()).collect(Collectors.toList());
//
//		// Throws error in case of the car not found
//		if (found.isEmpty())
//			return Response.status(Status.BAD_REQUEST).entity("Car not found").build();
//
//		Car updateCar = found.get(0);
//		cars.remove(updateCar);
//		return Response.noContent().build();
	}

	@HEAD
	public Response pingUsersService() {
		return Response.noContent().header("running", true).build();
	}
	



}