package acastemi.cars.rest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.powermock.api.mockito.PowerMockito;

import acastemi.cars.control.CarService;
import acastemi.cars.control.PersistenceService;
import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;
import acastemi.cars.util.ValidatorUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidatorUtil.class)
public class CarResourceTest {

	private static CarResource carResource;

	@BeforeClass
	public static void setUp() throws Exception {

		carResource = new CarResource();
		carResource.carSvc = mock(CarService.class);
		carResource.carSvc.persistenceService = mock(PersistenceService.class);

	}

	@Test
	final public void testFindAllCars() {

		List<Car> cars = new ArrayList<>();

		Car car = mock(Car.class);

		cars.add(car);

		when(carResource.carSvc.getAll()).thenReturn(cars);

		Response responseExpected = Response.ok(cars, MediaType.APPLICATION_JSON).build();

		assertEquals(responseExpected.getStatus(), carResource.findAllCars().getStatus());

		assertEquals(cars, responseExpected.getEntity());

	}

	@Test
	final public void testFindCar() throws EntityNotFoundException {

		Car car = mock(Car.class);
		when(car.getId()).thenReturn(8);

		when(carResource.carSvc.get(car.getId())).thenReturn(car);

		Response responseExpected = Response.ok(car, MediaType.APPLICATION_JSON).build();

		assertEquals(responseExpected.getStatus(), carResource.findCar(car.getId()).getStatus());

		assertEquals(car, responseExpected.getEntity());

	}
	
	@Test
	public void testFindEntityNotFound() throws EntityNotFoundException {
		
		Car car = mock(Car.class);
		when(car.getId()).thenReturn(7);

		when(carResource.carSvc.get(car.getId())).thenThrow(EntityNotFoundException.class);

		Response responseExpected = Response.status(Status.NOT_FOUND)
				.entity("{\"error\": \"The car with the id " + car.getId() + " does not exist.\"}").build();

		assertEquals(responseExpected.getStatus(), carResource.findCar(car.getId()).getStatus());
		
		
	}

	@Test
	final public void testCreateCar() {

		Car car = mock(Car.class);

		ArrayList<String> validationErrors = new ArrayList<String>();

		PowerMockito.mockStatic(ValidatorUtil.class);

		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);

		when(carResource.carSvc.create(car)).thenReturn(car);

		Response response = Response.accepted().entity(car).build();

		Response responseCreate = carResource.createCar(car);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());

		
	}
	
	
	@Test
	final public void testCreateCarFailed() {
		
		Car car = mock(Car.class);

		ArrayList<String> validationErrors = new ArrayList<String>();
		
		PowerMockito.mockStatic(ValidatorUtil.class);
		
		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);

		validationErrors.add("Some error");
		validationErrors.add("Another error");

		Response response = Response.status(400).entity(validationErrors).build();

		Response responseCreate = carResource.createCar(car);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
	}

	@Test
	final public void testUpdateCar() throws EntityNotFoundException {

		Car car = mock(Car.class);
		when(car.getId()).thenReturn(8);

		ArrayList<String> validationErrors = new ArrayList<String>();

		PowerMockito.mockStatic(ValidatorUtil.class);

		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);

		when(carResource.carSvc.update(car, car.getId())).thenReturn(car);

		Response response = Response.ok(car, MediaType.APPLICATION_JSON).build();
		
		Response responseCreate = carResource.updateCar(car, car.getId());

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
		

	}
	
	@Test
	final public void testUpdateCarNotFound() throws EntityNotFoundException {
		
		Car car = mock(Car.class);
		when(car.getId()).thenReturn(8);

		ArrayList<String> validationErrors = new ArrayList<String>();
		
		PowerMockito.mockStatic(ValidatorUtil.class);

		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);
		
		validationErrors.clear();
		
		when(carResource.carSvc.update(car, car.getId())).thenThrow(EntityNotFoundException.class);

		Response response = Response.status(Status.NOT_FOUND).entity("{\"error\": \"The car with the id " + car.getId() + " does not exist.\"}")
				.build();
				
		Response responseCreate = carResource.updateCar(car, car.getId());

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
		
	}
	
	@Test
	final public void testUpdateCarNotValid() throws EntityNotFoundException {
		
		Car car = mock(Car.class);
		when(car.getId()).thenReturn(8);

		ArrayList<String> validationErrors = new ArrayList<String>();
		
		PowerMockito.mockStatic(ValidatorUtil.class);
		
		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);

		validationErrors.add("Some error");
		validationErrors.add("Another error");

		Response response = Response.status(Status.BAD_REQUEST).entity(validationErrors)
				.build();
				
		Response responseCreate = carResource.updateCar(car, car.getId());

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
		
	}

	@Test
	final public void testDeleteCar() {

		Car car = mock(Car.class);
		car.setId(8);

		Response response = Response.noContent().build();

		assertEquals(response.getStatus(), carResource.deleteCar(car.getId()).getStatus());

		assertNull(response.getEntity());

	}
	
	@Test
	public void testDeleteEntityNotFound() throws EntityNotFoundException {

		Car car = mock(Car.class);
		when(car.getId()).thenReturn(7);
		

		doThrow(EntityNotFoundException.class).when(carResource.carSvc).delete(car.getId());

		Response response = Response.status(Status.NOT_FOUND)
				.entity("{\"error\": \"The car with the id " + car.getId() + " does not exist.\"}").build();

		assertEquals(response.getStatus(), carResource.deleteCar(car.getId()).getStatus());

		Response responseDelete = carResource.deleteCar(car.getId());

		assertEquals(responseDelete.getEntity(), response.getEntity());
		
		
	}

}
