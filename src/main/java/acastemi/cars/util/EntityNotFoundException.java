package acastemi.cars.util;

public class EntityNotFoundException extends Exception { 
    public EntityNotFoundException() {
        super("The entity does not exist.");
    }
}