package acastemi.cars.control;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;

@Stateless
public class PersistenceService {
	
	/**
	 * The entity manager object that it's injected by the container and created 
	 * by the Persistence Unit JPA_PU.
	 * All the transactions will be managed automatically.
	 */
	@PersistenceContext(unitName = "em_payara")
	protected EntityManager em;

	
	public void delete(final Car car) {
		
		em.remove(car);
		
	}

	public <T> T update(final T t) {

		
		return em.merge(t);

	}

	public <T> T create(final T t) {

		em.persist(t);

		return t;
	}

	public <T> T find(final Class<T> type, final int id) throws EntityNotFoundException {
		
		T t = em.find(type, id);
		
		if(t==null)
		  throw new EntityNotFoundException();
		else {
			return t;
		}
		  
	} 

	public <T> List<T> findAll(final Class<T> type) {
		
        final String className = type.getName();
        final TypedQuery<T> query = em
                .createQuery("SELECT data FROM " + className + " data", type);
        return query.getResultList();
        
    } 


}