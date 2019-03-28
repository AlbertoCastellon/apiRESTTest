package control;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.Gson;

import apiRESTTest.entity.Car;
import apiRESTTest.util.HibernateUtil;

@Stateless
public class CarService {
	
	@PersistenceContext(unitName="my_persistence_unit")
	private EntityManager em;
	
	public Response addCar(Car car) {
		
	     
	    em.getTransaction().begin();
	    em.persist(car);
	    em.getTransaction().commit();

        return Response.ok(car).build();
        
    }

    public Response deleteCar(String carId) {
    	
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Car car = (Car) session.load(Car.class, (carId));
            session.delete(car);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return Response.noContent().build();
    }

    public Response updateCar(Car car) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return Response.ok(car).build();
    }

    @SuppressWarnings("unchecked")
	public Response getAllCars() {
    	
        List<Car> cars = new ArrayList<Car>();
        Transaction trns;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            cars = session.createQuery("from Car").list();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
       
        return Response.ok(new Gson().toJson(cars), MediaType.APPLICATION_JSON).build();
    }
    

    public Response getCarById(String carId) {
    	
    	Car car = null;
    	car = em.find(Car.class, carId);
    	
    	return Response.ok(car, MediaType.APPLICATION_JSON).build();

//        Car car = null;
//        Transaction trns = null;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        try {
//            trns = session.beginTransaction();
//            String queryString = "from Car where id = :id";
//            Query query = session.createQuery(queryString);
//            query.setString("id", carId);
//            car = (Car) query.uniqueResult();
//        } catch (RuntimeException e) {
//            e.printStackTrace();
//        } finally {
//            session.flush();
//            session.close();
//        }
//        return Response.ok(car, MediaType.APPLICATION_JSON).build();
    }
}
