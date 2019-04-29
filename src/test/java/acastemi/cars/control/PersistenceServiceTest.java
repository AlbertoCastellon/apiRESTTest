package acastemi.cars.control;


import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class PersistenceServiceTest {

	private static final int ID = 1;
	
	@InjectMocks
	private PersistenceService persistenceService;
	
	@Mock
	EntityManager em;
	
	@Rule public MockitoRule mockitoRule = MockitoJUnit.rule(); 
	

	@Test
	final public void testDelete() {
		
		Car car = mock(Car.class);
        
        doNothing().when(em).remove(any());

        persistenceService.delete(car);

        verify(em).remove(car);
        
	}

	@Test
	final public void testUpdate() {
		
		Car expectedCar = mock(Car.class);

		when(em.merge(any()))
        .thenReturn(expectedCar);
        
        Car returnedCar = persistenceService.update(expectedCar);

        verify(em).merge(expectedCar);
        
        assertEquals(expectedCar, returnedCar);
        
	}

	@Test
	final public void testCreate() {
		
		Car expected = mock(Car.class);

        doNothing().when(em).persist(any());

        Car returnedCar = persistenceService.create(expected);

        verify(em).persist(expected);
        
        assertEquals(expected, returnedCar);
        
	}

	@Test
	final public void testFind() throws EntityNotFoundException {
		
		Car expectedCar = mock(Car.class);

        when(em.find(any(), anyInt()))
                .thenReturn(expectedCar);

        Car returnedCar = persistenceService.find(Car.class, ID);

        assertEquals(expectedCar, returnedCar);

        
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testFindEntityNotFound() throws EntityNotFoundException {
		
		
		when(em.find(any(), anyInt()))
        .thenReturn(null);
		
		persistenceService.find(Car.class, ID);
		
	}

	
	@Test
	final public <T> void testFindAll() {
		
		List<Object> carsExpected = new ArrayList<>();
		
        Car car = mock(Car.class);
        when(car.getId()).thenReturn(8);

        carsExpected.add(car);
        
        TypedQuery<Object> mockedQuery = mock(TypedQuery.class);
        
        when(em.createQuery(anyString(), any())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(carsExpected);
        
        List<Car> carsActual = persistenceService.findAll(Car.class);

        
        assertEquals(carsExpected, carsActual);
        
	}

}
