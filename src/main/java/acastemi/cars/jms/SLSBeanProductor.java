package acastemi.cars.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 
 * Creates a connection to a JMS Queue to be able to write messages with petitions to perform CRUD actions on the DB.
 *
 */
@Stateless
public class SLSBeanProductor {

	@Resource(name = "myQueueConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(lookup = "myQueue")
	private Queue queue;
	
	/**
	 * 
	 * @param crud type of CRUD action in the petition to perform (CREATE, UPDATE, DELETE)
	 * @param msg body of the message containing a Car object, or empty, as in DELETE
	 * @param carId id of the car to UPDATE or DELETE, -1 in the case of the CREATE
	 * @throws JMSException
	 */
	public void sendJMSMessage(String crud, String msg, int carId) throws JMSException {
		
		Connection connection = null;
		Session session = null;
		try {
			
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(true, 0);

			TextMessage tm = session.createTextMessage(msg);

			tm.setStringProperty("CRUD", crud);
			tm.setIntProperty("carId", carId);
			
			MessageProducer messageProducer = session.createProducer(queue);
			messageProducer.send(tm);

		} finally {
			
			if (session != null) {
				session.close();
			}
			if (connection != null) {
				connection.close();
			}
			
		}
	}
	
}