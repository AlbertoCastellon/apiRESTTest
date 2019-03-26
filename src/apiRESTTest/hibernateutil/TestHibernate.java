package apiRESTTest.hibernateutil;

import java.sql.Timestamp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import apiRESTTest.Car;


public class TestHibernate {
	
	private static SessionFactory factory;

	public static void main(String[] args) {
		
		factory = new HibernateUtil().getSessionFactory();
		
		Car car = new Car("BMW", new Timestamp(System.currentTimeMillis()), "Germany",
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
