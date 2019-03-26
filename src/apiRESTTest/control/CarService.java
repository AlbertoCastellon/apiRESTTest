package apiRESTTest.control;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transaction;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.persistence.sessions.Session;

import apiRESTTest.entity.Car;

public class CarService {

	public Response addCar(Car car) {
		
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(car);
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

    public Response deleteCar(int carId) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            Car car = (Car) session.load(Car.class, new Integer(carId));
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

    public Response getAllCars() {
        List<Car> cars = new ArrayList<Car>();
        Transaction trns = null;
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
        return Response.ok(carListIterator(cars), MediaType.APPLICATION_JSON).build();
    }

    public Response getCarById(int carId) {
        Car car = null;
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            String queryString = "from User where id = :id";
            Query query = session.createQuery(queryString);
            query.setInteger("id", carId);
            car = (Car) query.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            session.flush();
            session.close();
        }
        return Response.ok(car, MediaType.APPLICATION_JSON).build();
    }
    
    private String carListIterator(List<Car> cars2) {

		StringBuffer strBuffer = new StringBuffer();
		for (Car c : cars2) {
			strBuffer.append(c.toString() + "\n");
		}
		return strBuffer.toString();
	}
}
