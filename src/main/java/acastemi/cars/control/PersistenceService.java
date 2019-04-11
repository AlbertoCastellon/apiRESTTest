package acastemi.cars.control;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public abstract class PersistenceService {
	
	/**
	 * The entity manager object that it's injected by the container and created 
	 * by the Persistence Unit JPA_PU.
	 * All the transactions will be managed automatically.
	 */
	@PersistenceContext(unitName = "em_payara")
	protected EntityManager em;

	public PersistenceService() {
		super();
	}

	protected static final Logger LOGGER = Logger.getLogger(CarService.class);

	public abstract boolean delete(final int id);

	public abstract Object update(final Object updated, int id);

	public abstract Object create(final Object object);

	public abstract Object get(final int id);

	public abstract List<Object> getAll();


}