package acastemi.cars.control;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

import acastemi.cars.entity.Car;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

class PersistenceServiceTest {

	private static final int ID = 1;
	private PersistenceService persistenceService;

	@BeforeEach
	void setUp() throws Exception {
		
		this.persistenceService = new PersistenceService(); 
        this.persistenceService.em = mock(EntityManager.class);
	}

	@Test
	final void testDelete() {
		
		Car car = new Car();
        
        doNothing().when(this.persistenceService.em).remove(Matchers.anyObject());

        this.persistenceService.delete(car);

        verify(this.persistenceService.em).remove(car);
        
	}

	@Test
	final void testUpdate() {
		
		Car car = new Car();

		when(this.persistenceService.em.merge(Matchers.anyObject()))
        .thenReturn(car);
        
        Car returnedCar = this.persistenceService.em.merge(car);

        verify(this.persistenceService.em).merge(car);
        
        assertEquals(car, returnedCar);
	}

	@Test
	final void testCreate() {
		
		Car car = new Car();

        doNothing().when(this.persistenceService.em).persist(anyObject());

        Car returnedCar = this.persistenceService.create(car);

        verify(this.persistenceService.em).persist(car);
        
        assertEquals(car, returnedCar);
        
	}

	@Test
	final void testFind() {
		
		Car expected = new Car();

        when(this.persistenceService.em.find(anyObject(), anyInt()))
                .thenReturn(expected);

        Car actual = this.persistenceService.find(Car.class, ID);

        assertEquals(actual, expected);
        
	}

	@Disabled
	@Test
	final <T> void testFindAll() {
		
		List<Object> cars = new ArrayList<>();

        Car car = mock(Car.class);
        when(car.getId()).thenReturn(8);

        cars.add(car);       
        
        TypedQuery<Object> mockedQuery = mock(TypedQuery.class);
        
        when(this.persistenceService.em.createQuery(anyString(), any())).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(cars);
               
//        Query mockedQuery = mock(Query.class);
//        when(mockedQuery.getResultList()).thenReturn(cars);
//        when(mockedQuery.setParameter(
//                anyString(), anyObject())
//        ).thenReturn(mockedQuery);
        
        cars = this.persistenceService.findAll(Car.class);

        assertEquals(cars.size(), 1);
        
	}

}
