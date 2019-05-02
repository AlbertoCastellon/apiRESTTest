package acastemi.cars.jms;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import acastemi.cars.control.CarService;
import acastemi.cars.entity.Car;
import acastemi.cars.util.EntityNotFoundException;


/**
 * 
 * Stateless, server-side, transaction-aware component 
 * that is driven by a Java message (javax.jms.message). It is invoked by the EJB 
 * Container when a message is received from the JMS Queue. 
 * 
 *
 */
@MessageDriven(mappedName = "myQueue")  
public class MyListener implements MessageListener {  
	  
	/**
	 * Initializes the Enterprise Java Bean
	 */
	@EJB
	protected CarService carSvc;
	
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	
	private final static Logger LOGGER = Logger.getLogger(MyListener.class);
	
	/**
	 * It executes the action (create,update,delete), when a JMS petition is on the Queue
	 */
    public void onMessage(Message m) {  
    	
        try {  
        	
	        TextMessage msg=(TextMessage) m;
	   	
	        if(msg.getStringProperty("CRUD").equals("CREATE")) {
	        	
	        	LOGGER.info("Creating a Car from a Queue petition");
	        
	        	carSvc.create( JSON_MAPPER.readValue(msg.getText(), Car.class));
	        
	        } else if (msg.getStringProperty("CRUD").equals("DELETE")) {
	        	
	        	LOGGER.info("Deleting a Car from a Queue petition");
	        
				carSvc.delete(msg.getIntProperty("carId"));
	        
	        } else if (msg.getStringProperty("CRUD").equals("UPDATE")) {
	        	
	        	LOGGER.info("Updating Car from a Queue petition");
	        	
	        	carSvc.update( JSON_MAPPER.readValue(msg.getText(), Car.class), msg.getIntProperty("carId"));
				
			}
	     
        
        } catch(JMSException | EntityNotFoundException | IOException e){
        	
        	LOGGER.error(e);
        	
        }  
    }  
}  