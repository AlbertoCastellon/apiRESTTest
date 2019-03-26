package apiRESTTest;

import java.sql.Timestamp;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestHibernate {
	
	private static SessionFactory factory; 

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		try {
			
			factory = new Configuration().configure().buildSessionFactory();
			
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		Car car = new Car(0, "BMW", new Timestamp(System.currentTimeMillis()), "Germany",
				new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));

		Integer carID1 = addCar(car);
		
	}

	public static Integer addCar(Car car) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		int carID = 0;

		try {
			tx = session.beginTransaction();
			carID = (Integer) session.save(car);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return carID;
		
	}
}
