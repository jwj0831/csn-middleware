package cir.csn.server.snm.subs;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONObject;

import cir.csn.server.snm.db.DAOFactory;
import cir.csn.server.snm.db.SensorDataDAO;

public class SimpleMQTTSubscriber implements MqttCallback  {
	MqttClient myClient;
	MqttConnectOptions connOpt;
	
	static Logger logger = Logger.getLogger(SimpleMQTTSubscriber.class);
	
	public SensorDataDAO dao;
	static final String BROKER_URL = "tcp://localhost:1883";
	static final String NODE_ID = "Node2";
	//static final String CONN_USERNAME = "user";
	//static final String CONN_PASSWORD_MD5 = "1234";
	
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
	}
	
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		String data = arg1.toString();
		SensorDataParser parser = new SensorDataParser(data);
		JSONObject jsonObj = parser.getJsonObj();
		System.out.println("Sensor ID: " + jsonObj.get("snsr_id"));
		System.out.println("Timestamp: " + jsonObj.get("timestamp"));
		System.out.println("Value: " + jsonObj.get("snsr_val"));
		
		SensorData sensorData  = new SensorData( jsonObj.get("snsr_id").toString(), jsonObj.get("timestamp").toString(), jsonObj.get("snsr_val").toString() );
		dao.add(sensorData);
		System.out.println("DB Input Finish");
	}

	public static void main(String[] args) {
		logger.setLevel(Level.ALL);
		
		SimpleMQTTSubscriber subscriber = new SimpleMQTTSubscriber();
		subscriber.dao = new DAOFactory().sensorDataDAO();
		subscriber.subscribeSensorData();
	}

	public void subscribeSensorData() {
		// setup MQTT Client
		String clientID = NODE_ID;
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		//connOpt.setUserName(CONN_USERNAME);
		//connOpt.setPassword(CONN_PASSWORD_MD5.toCharArray());

		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Connected to " + BROKER_URL);

		// Setup topic
		// topics on m2m.io are in the form <domain>/<stuff>/<thing>
		String myTopic = "Node1";

		// Subscribe to topic if subscriber
		try {
			int subQoS = 1;
			myClient.subscribe(myTopic, subQoS);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

		// Disconnect
		try {
			// Wait to ensure subscribed messages are delivered
			System.out.println("wait..");
			for(int i=1;i<=60;i++){
				Thread.sleep(1000);
			}
			
			myClient.disconnect();
			System.out.println("Disconneted..\n\nEnd...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
