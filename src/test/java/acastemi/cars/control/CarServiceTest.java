package acastemi.cars.control;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

	@InjectMocks
	private CarService carService;
	
	@Mock
	PersistenceService persistenceService;
	
	@Test
	final public void testGetAll() {

		List<Car> cars = new ArrayList<>();

		when(persistenceService.findAll(Car.class)).thenReturn(cars);

		assertEquals(cars, carService.getAll());

	}

	@Test
	final public void testGet() throws EntityNotFoundException {

		Car carExpected = new Car();
		carExpected.setId(8);

		when(persistenceService.find(Car.class, carExpected.getId())).thenReturn(carExpected);

		Object carReturned = carService.get(carExpected.getId());

		assertEquals(carExpected, carReturned);

	}

	@Test
	final public void testCreate() {

		Car carExpected = new Car();

		when(carService.persistenceService.create(carExpected)).thenReturn(carExpected);

		Car carReturned = carService.create(carExpected);

		assertEquals(carExpected, carReturned);

	}

	@Test
	final public void testUpdate() throws EntityNotFoundException {
		
		Car carExpected = new Car();
		carExpected.setId(8);

		when(carService.persistenceService.find(Car.class,carExpected.getId())).thenReturn(carExpected);
		
		when(carService.persistenceService.update(carExpected)).thenReturn(carExpected);

		Object returnedCar = carService.update(carExpected, carExpected.getId());

		assertEquals(returnedCar, carExpected);

		
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testUpdateEntityNotFound() throws EntityNotFoundException {
		
		Car car = new Car();
		car.setId(9);
		
		when(carService.persistenceService.find(Car.class,car.getId())).thenThrow(EntityNotFoundException.class);
		
		carService.update(car, car.getId());
		
		
	}

	@Test
	final public void testDelete() throws EntityNotFoundException {
		
		Car car = new Car();
		car.setId(8);

		when(carService.persistenceService.find(Car.class,car.getId())).thenReturn(car);

		carService.delete(car.getId());
		
		verify(carService.persistenceService).delete(car);
		
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testDeleteEntityNotFound() throws EntityNotFoundException {
		
		Car car = new Car();
		car.setId(9);
		
		when(carService.persistenceService.find(Car.class, car.getId())).thenThrow(EntityNotFoundException.class);
		
		carService.delete(car.getId());
		
		verify(carService.persistenceService, never()).delete(car);
		
		
	}

}
