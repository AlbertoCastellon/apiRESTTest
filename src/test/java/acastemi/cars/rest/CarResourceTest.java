package acastemi.cars.rest;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.powermock.api.mockito.PowerMockito;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;
import acastemi.cars.util.ValidatorUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidatorUtil.class)
public class CarResourceTest {

	private static CarResource carResource;

	@BeforeClass
	public static void setUp() throws Exception {

		carResource = new CarResource();
		carResource.carSvc = mock(CarService.class);

	}

	@Test
	final public void testFindAllCars() {

		List<Car> cars = new ArrayList<>();

		Car car = mock(Car.class);

		cars.add(car);

		when(carResource.carSvc.getAll()).thenReturn(cars);

		Response response = Response.ok(cars, MediaType.APPLICATION_JSON).build();

		assertTrue(carResource.findAllCars() instanceof Response);

		assertEquals(response.getStatus(), carResource.findAllCars().getStatus());

		assertEquals(cars, response.getEntity());

	}

	@Test
	final public void testFindCar() {

		Car car = mock(Car.class);
		car.setId(8);

		when(carResource.carSvc.get(car.getId())).thenReturn(car);

		Response response = Response.ok(car, MediaType.APPLICATION_JSON).build();

		assertTrue(carResource.findCar(8) instanceof Response);

		assertEquals(response.getStatus(), carResource.findCar(car.getId()).getStatus());

		assertEquals(car, response.getEntity());

		Car car2 = mock(Car.class);
		car2.setId(7);

		when(carResource.carSvc.get(car2.getId())).thenReturn(null);

		response = Response.status(404)
				.entity("{\"error\": \"The car with the id " + car2.getId() + " does not exist.\"}").build();

		assertTrue(carResource.findCar(car2.getId()) instanceof Response);

		assertEquals(response.getStatus(), carResource.findCar(car2.getId()).getStatus());

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

		assertTrue(responseCreate instanceof Response);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());

		
		
		
		validationErrors.add("Some error");
		validationErrors.add("Another error");

		response = Response.status(400).entity(validationErrors).build();

		responseCreate = carResource.createCar(car);

		assertTrue(responseCreate instanceof Response);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
	}

	@Test
	final public void testUpdateCar() {

		Car car = mock(Car.class);
		car.setId(0);

		ArrayList<String> validationErrors = new ArrayList<String>();

		PowerMockito.mockStatic(ValidatorUtil.class);

		when(ValidatorUtil.validate(car)).thenReturn(validationErrors);

		when(carResource.carSvc.update(car, car.getId())).thenReturn(car);

		Response response = Response.ok(car, MediaType.APPLICATION_JSON).build();
		
		Response responseCreate = carResource.updateCar(car, car.getId());
		
		assertTrue(responseCreate instanceof Response);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
		
		
		
		
		validationErrors.clear();
		
		when(carResource.carSvc.update(car, car.getId())).thenReturn(null);

		response =Response.status(400).entity("{\"error\": \"The car with the id " + car.getId() + " does not exist.\"}")
				.build();
				
		responseCreate = carResource.updateCar(car, car.getId());
		
		assertTrue(responseCreate instanceof Response);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());
		
		
		
		
		when(carResource.carSvc.update(car, car.getId())).thenReturn(car);

		response =Response.ok(car, MediaType.APPLICATION_JSON).build();
				
		responseCreate = carResource.updateCar(car, car.getId());
		
		assertTrue(responseCreate instanceof Response);

		assertEquals(response.getStatus(), responseCreate.getStatus());

		assertEquals(response.getEntity(), responseCreate.getEntity());

	}

	@Test
	final public void testDeleteCar() {

		Car car = mock(Car.class);
		car.setId(8);

		when(carResource.carSvc.delete(car.getId())).thenReturn(true);

		Response response = Response.noContent().build();

		assertTrue(carResource.deleteCar(8) instanceof Response);

		assertEquals(response.getStatus(), carResource.deleteCar(car.getId()).getStatus());

		assertNull(response.getEntity());

		Car car2 = mock(Car.class);
		car2.setId(7);

		when(carResource.carSvc.delete(car2.getId())).thenReturn(false);

		response = Response.status(400)
				.entity("{\"error\": \"The car with the id " + car2.getId() + " does not exist.\"}").build();

		assertEquals(response.getStatus(), carResource.deleteCar(car2.getId()).getStatus());

		Response responseDelete = carResource.deleteCar(car2.getId());

		assertTrue(responseDelete instanceof Response);

		assertEquals(responseDelete.getEntity(), response.getEntity());

	}

}
