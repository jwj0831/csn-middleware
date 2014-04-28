package cir.csn.server.snm.subs;

import java.util.Map;

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
import cir.csn.server.snm.metamangement.SensorMetaDAO;

public class SimpleMQTTSubscriber implements MqttCallback  {
	MqttClient myClient;
	MqttConnectOptions connOpt;

	static Logger logger = Logger.getLogger(SimpleMQTTSubscriber.class);

	public SensorDataDAO sensorDao;
	public SensorMetaDAO sensorMetaDao;
	static final String BROKER_URL = "tcp://localhost:1883";
	static final String CLIENT_ID = "Subs-Node";
	static final String SUBS_TOPIC = "CSN/Middleware/RawData";

	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		String data = arg1.toString();
		SensorDataParser parser = new SensorDataParser(data);
		JSONObject jsonObj = parser.getJsonObj();

		String id = jsonObj.get("snsr_id").toString();
		String timestamp = jsonObj.get("timestamp").toString();
		String val = jsonObj.get("snsr_val").toString();

		System.out.println("Sensor ID: " + id);
		System.out.println("Timestamp: " + jsonObj.get("timestamp"));
		System.out.println("Value: " + jsonObj.get("snsr_val"));

		Map<String, String> map = sensorMetaDao.getSensorMeta(id);
		String uri = map.get("uri");
		System.out.println("URI: " + uri);

		SensorData sensorData  = new SensorData(uri, timestamp, val);
		sensorDao.add(sensorData);
		System.out.println("DB Input Finish");
	}

	public static void main(String[] args) {
		logger.setLevel(Level.ALL);

		SimpleMQTTSubscriber subscriber = new SimpleMQTTSubscriber();
		subscriber.sensorDao = new DAOFactory().sensorDataDAO();
		subscriber.sensorMetaDao = new DAOFactory().sensorMetaDAO();
		subscriber.subscribeSensorData();
	}

	public void subscribeSensorData() {
		// setup MQTT Client
		String clientID = CLIENT_ID;
		connOpt = new MqttConnectOptions();

		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);

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
		String myTopic = SUBS_TOPIC;

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
			System.out.println("Press any key to stop Subscription...");
			System.in.read();
			myClient.disconnect();
			System.out.println("Disconneted..\n\nEnd...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
