package apiRESTTest.hibernateutil;


import java.util.logging.Logger;

import org.apache.log4j.BasicConfigurator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory factory;

	private SessionFactory buildSessionFactory() {

		BasicConfigurator.configure();

		try {

			factory = new Configuration().configure().buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		
		return factory;
		
	}
	
	public SessionFactory getSessionFactory() {
		
		return buildSessionFactory();
		
	}

}
