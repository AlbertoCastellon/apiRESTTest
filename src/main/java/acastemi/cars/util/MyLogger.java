package acastemi.cars.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * A class to setup the Logger and it's formatters and handlers
 *
 */
public class MyLogger {

	private static FileHandler fileTxt;
	private static SimpleFormatter formatterTxt;

	private static FileHandler fileHTML;
	private static Formatter formatterHTML;
	
	/**
	 * get the global logger to configure it, suppress the logging output to the console, 
	 * sets the logger level to INFO, creates a TXT and a HTML formatter 
	 * @throws IOException in case there is some error with the file creation/reading/writing
	 */
	
	public static void setup() throws IOException {
		
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		Logger rootLogger = Logger.getLogger("");
		Handler[] handlers = rootLogger.getHandlers();
		if (handlers[0] instanceof ConsoleHandler) {
			rootLogger.removeHandler(handlers[0]);
		}

		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");

		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

		formatterHTML = new MyHtmlFormatter();
		fileHTML.setFormatter(formatterHTML);
		logger.addHandler(fileHTML);
		
	}
}