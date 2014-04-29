package cir.csn.server.snm.pub;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import cir.csn.server.snm.subs.SensorData;

public class SensorNetworkDataPublishThread extends Thread {
	private final String connectionUri = "tcp://localhost:61616";
	private ActiveMQConnectionFactory connectionFactory;
	private Connection connection;
	private Session session;
	private Destination destination;
	private Set<String> networkListSet = null;
	private Map<String, Set<String>> networkListMap = null;
	
	SensorData sensorData;
	static final int SLEEP_TIME = 100;
	
	public void before() throws Exception {
		connectionFactory = new ActiveMQConnectionFactory(connectionUri);
		connection = connectionFactory.createConnection();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	public void after() throws Exception {
		if (connection != null) {
			connection.close();
		}
	}

	public void send() throws Exception {
		System.out.println("[Data Pub Thread] Loop Before");
		while(true){
			if( SensorNetworkDataPublisher.getSensorDataQueue().peek() != null ){
				pubSensorDataTONewNetwork();
			}
			else{
				try{Thread.sleep(SLEEP_TIME);}catch(InterruptedException ie){}
			}
		}
	}

	private void pubSensorDataTONewNetwork() throws JMSException {
		System.out.println("[Data Pub Thread] Loop Exit");
		sensorData = SensorNetworkDataPublisher.getSensorDataQueue().poll();
		System.out.println("[Data Pub Thread] Sensor URI: " + sensorData.getSnsr_id());
		Iterator<String> iter = networkListSet.iterator();
		while(iter.hasNext()) {
			System.out.println("[Data Pub Thread] Sensor Network Iteration"); 
			String tempSNName = (String) iter.next();
			System.out.println("[Data Pub Thread] Sensor Network Name: " + tempSNName);
			Set<String> tempSensors = networkListMap.get(tempSNName);
			Iterator<String> iter2 = tempSensors.iterator();
			while(iter2.hasNext()) {
				String sensor = (String) iter2.next();
				System.out.println("[Data Pub Thread]\tName: " + sensor);
			}
			
			if(tempSensors.contains(sensorData.getSnsr_id())) {
				destination = session.createTopic(tempSNName);
				MessageProducer producer = session.createProducer(destination);
				Message message = session.createMessage();
				message.setStringProperty("id", sensorData.getSnsr_id());
				message.setStringProperty("timestamp", sensorData.getTimestamp());
				message.setStringProperty("val", sensorData.getValue());
				System.out.println("Send to Network: " + tempSNName);
				System.out.println("Sensor ID: " + sensorData.getSnsr_id() + " timestamp: " + sensorData.getTimestamp() + " val: " +sensorData.getValue());
				producer.send(message);
				producer.close();
			}
		}
	}

	public void run() {
		System.out.println("Starting example Stock Ticker Publisher now...");
		try {
			before();
			send();
			after();
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("Caught an exception during the example: " + e.getMessage());
		}
		System.out.println("Finished running the sample Stock Ticker Publisher app.");
	}

	public void setNetworkListSet(Set<String> networkListSet) {
		this.networkListSet = networkListSet;
	}

	public void setNetworkListMap(Map<String, Set<String>> networkListMap) {
		this.networkListMap = networkListMap;
	}
}
