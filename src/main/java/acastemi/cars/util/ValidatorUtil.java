package acastemi.cars.util;

import java.util.ArrayList;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Validates an object against the restrictions put in the Bean validations
 *
 */
public class ValidatorUtil {

	private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static Validator validator = factory.getValidator();

	/**
	 * 
	 * @param <T>
	 * @param entity any entity that needs to be validated
	 * @return an ArrayList of Strings containing all the error messages thrown by the unsuccessful validations
	 * 
	 */
	public static <T> ArrayList<String> validate(T entity) {
		
		Set<ConstraintViolation<T>> violations = validator.validate(entity);
		ArrayList<String> violationMessages = new ArrayList<String>();
		for (ConstraintViolation<T> violation : violations) {
			violationMessages.add(violation.getMessage());
		}
		
		return violationMessages;
	}

}