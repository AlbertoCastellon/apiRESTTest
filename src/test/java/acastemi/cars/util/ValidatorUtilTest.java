package acastemi.cars.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import acastemi.cars.entity.Brand;
import acastemi.cars.entity.Car;
import acastemi.cars.entity.Country;

class ValidatorUtilTest {

	private ArrayList<String> violationMessages = new ArrayList<String>();
	
	private ArrayList<String> posibleViolationMessages = new ArrayList<String> ();
	
	@BeforeEach
	final void setup() {
		
		posibleViolationMessages.add("Registration cannot be empty.");
		posibleViolationMessages.add("Country may not be empty.");	
		posibleViolationMessages.add("Brand cannot be empty.");
			
		
	}
	

	@Test
	final void testValidate() {
		
		
		Car car = new Car(0, new Brand(0 ,"BMW"), new Timestamp(System.currentTimeMillis()), new Country(0, "Germany"), new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));

		violationMessages = ValidatorUtil.validate(car);
		
		assertTrue(violationMessages.isEmpty());
		
		
		
		Car emptyCar = new Car();

		violationMessages = ValidatorUtil.validate(emptyCar);
		
		assertFalse(violationMessages.isEmpty());
		
		for(int i = 0; i<violationMessages.size(); i++) {			
			
			assertTrue(violationMessages.contains(posibleViolationMessages.get(i)));
			
		}
		
		
	}

}
