package acastemi.cars.jms;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.ArgumentMatchers.eq;


import javax.jms.TextMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;

@RunWith(MockitoJUnitRunner.class)
public class AsyncCarServiceTest {
	
	
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	
	@InjectMocks
	private AsyncCarService asyncCarService;
	
	@Mock
	private CarService carService;


	@Test
	public final void testOnMessageCreateCar() throws Exception {
		
		Car car = new Car();
		
		TextMessage txtMessage =  mock(TextMessage.class);
		
		when(txtMessage.getStringProperty("CRUD")).thenReturn("CREATE");
		
		when(txtMessage.getText()).thenReturn(JSON_MAPPER.writeValueAsString(car));
		
		asyncCarService.onMessage(txtMessage);
		
		verify(carService).create(refEq(car));
		
	}
	
	@Test
	public final void testOnMessageDeleteCar() throws Exception {
		
		
		TextMessage txtMessage =  mock(TextMessage.class);
		
		when(txtMessage.getStringProperty("CRUD")).thenReturn("DELETE");
		when(txtMessage.getIntProperty("carId")).thenReturn(2);
		
		
		asyncCarService.onMessage(txtMessage);
		
		verify(carService).delete(2);
		
	}
	
	@Test
	public final void testOnMessageUpdateCar() throws Exception {
		
		Car car = new Car();
		
		TextMessage txtMessage =  mock(TextMessage.class);
		
		when(txtMessage.getStringProperty("CRUD")).thenReturn("UPDATE");
		when(txtMessage.getIntProperty("carId")).thenReturn(2);
		
		when(txtMessage.getText()).thenReturn(JSON_MAPPER.writeValueAsString(car));
		
		asyncCarService.onMessage(txtMessage);
		
		verify(carService).update(refEq(car), eq(2));
		
	}

}
