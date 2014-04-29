package cir.csn.client;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.ActiveMQConnectionFactory;


public class SimpleTestSubscriber {
	private final String connectionUri = "tcp://localhost:61616";
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	static final String TEST_TOPIC = "CSN.TEST.TOPIC";

	public void before() throws Exception {
		connectionFactory = new ActiveMQConnectionFactory(connectionUri);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		destination = session.createTopic(TEST_TOPIC);
	}

	public void after() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}

	public void run() throws Exception {
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(new EventListener());
		TimeUnit.MINUTES.sleep(120);
		connection.stop();
		consumer.close();
	}

	public static void main(String[] args) {
		SimpleTestSubscriber producer = new SimpleTestSubscriber();
		System.out.print("\n\n\n");
		System.out.println("Starting example Stock Ticker Subscriber now...");
		try {
			producer.before();
			producer.run();
			producer.after();
		} catch (Exception e) {
			System.out.println("Caught an exception during the example: " + e.getMessage());
		}
		System.out.println("Finished running the sample Stock Ticker Subscriber app.");
		System.out.print("\n\n\n");
	}
	
	public class EventListener implements MessageListener {

	    public void onMessage(Message message) {
	        try {
	        	String sn_uri = message.getStringProperty("id");
				String timestamp = message.getStringProperty("timestamp");
				String val = message.getStringProperty("val");
				System.out.println("Sensor ID: " + sn_uri + " timestamp: " + timestamp + " val: " + val);
	        	
	        } catch (Exception e) {
	            System.out.println("Worker caught an Exception");
	        }
	    }
	}

}