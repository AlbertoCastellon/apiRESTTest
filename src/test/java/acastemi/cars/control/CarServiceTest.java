package acastemi.cars.control;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import acastemi.cars.entity.Car;

class CarServiceTest {

	private CarService carService;

	@BeforeEach
	void setUp() throws Exception {

		carService = new CarService();
		carService.persistenceService = mock(PersistenceService.class);

	}

	@Test
	final void testGetAll() {

		List<Car> cars = new ArrayList<>();

		when(carService.persistenceService.findAll(Car.class)).thenReturn(cars);

		cars = carService.getAll();

		verify(carService.persistenceService).findAll(Car.class);

		assertTrue(carService.getAll() instanceof List<?>);

//		List<Car> cars = new ArrayList<>();       
//
//        when(carService.persistenceService.findAll(Car.class)).thenReturn(cars);
//        
//        cars = carService.getAll();
//
//        assertEquals(cars.size(), 0);
//        
//        
//        
//        Car car = mock(Car.class);
//
//        cars.add(car);
//        
//        cars = carService.getAll();
//
//        assertEquals(cars.size(), 1);

	}

	@Test
	final void testGet() {

		Car car = new Car();
		car.setId(8);

		when(carService.persistenceService.find(Car.class, car.getId())).thenReturn(car);

		Object returnedCar = carService.get(car.getId());

		verify(carService.persistenceService).find(Car.class, car.getId());

		assertTrue(returnedCar instanceof Car);

//		Car car = mock(Car.class);
//		car.setId(8);
//
//        when(carService.persistenceService.find(Car.class, 8)).thenReturn(car);
//        
//        Car carRetrieved = carService.get(8);
//
//        assertEquals(car, carRetrieved);

	}

	@Test
	final void testCreate() {

		Car car = new Car();

		when(carService.persistenceService.create(car)).thenReturn(car);

		Object returnedCar = carService.create(car);

		verify(carService.persistenceService).create(car);

		assertTrue(returnedCar instanceof Car);

	}

	@Test
	final void testUpdate() {
		
		Car car = new Car();
		car.setId(8);

		when(carService.persistenceService.find(Car.class,car.getId())).thenReturn(car);
		
		when(carService.persistenceService.update(car)).thenReturn(car);

		Object returnedCar = carService.update(car, car.getId());

		verify(carService.persistenceService).find(Car.class,car.getId());
		
		verify(carService.persistenceService).update(car);

		assertEquals(returnedCar, car);
		
		
		Car car2 = new Car();
		car2.setId(9);
		
		when(carService.persistenceService.find(Car.class,car2.getId())).thenReturn(null);
		
		Object returnedCar2 = carService.update(car2, car2.getId());
		
		verify(carService.persistenceService).find(Car.class,car.getId());
		
		verify(carService.persistenceService, never()).update(car2);

		assertNotEquals(returnedCar2, car2);
		
		assertNull(returnedCar2);
		
		
	}

	@Test
	final void testDelete() {
		
		Car car = new Car();
		car.setId(8);

		when(carService.persistenceService.find(Car.class,car.getId())).thenReturn(car);

		boolean carDeleted = carService.delete(car.getId());

		verify(carService.persistenceService).find(Car.class,car.getId());
		
		verify(carService.persistenceService).delete(car);

		assertTrue(carDeleted);
		
		
		Car car2 = new Car();
		car2.setId(9);
		
		when(carService.persistenceService.find(Car.class,car2.getId())).thenReturn(null);
		
		carDeleted = carService.delete(car2.getId());
		
		verify(carService.persistenceService).find(Car.class,car.getId());
		
		verify(carService.persistenceService, never()).delete(car2);

		assertFalse(carDeleted);
	}

}
