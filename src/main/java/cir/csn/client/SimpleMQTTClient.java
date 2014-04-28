package cir.csn.client;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.simple.JSONObject;

public class SimpleMQTTClient implements MqttCallback {
	MqttClient myClient;
	MqttConnectOptions connOpt;
	static final String BROKER_URL = "tcp://localhost:1883";
	static final String NODE_ID = "Node2";
	static final String TOPIC_NAME = "CSN/Middleware/RawData";
	static final int MSG_NUM = 100;

	
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
	}
	
	public void deliveryComplete(MqttDeliveryToken arg0) {
	}
	
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
	}
		
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}

	public static void main(String[] args) {
		SimpleMQTTClient publisher = new SimpleMQTTClient();
		publisher.publishSensorData();
	}

	public void publishSensorData() {
		// Setup MQTT Client
		String clientID = NODE_ID;
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
		String myTopic = TOPIC_NAME;
		MqttTopic topic = myClient.getTopic(myTopic);
		int randNum = 0;
		for (int i=1; i<=MSG_NUM; i++) {
			Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
			
            Random oRandom = new Random();

            // 1~10까지의 정수를 랜덤하게 출력
            randNum = oRandom.nextInt(999) + 1;
            
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("snsr_id", NODE_ID);
			jsonObject.put("timestamp",today);
			jsonObject.put("snsr_val", randNum);
			
			String pubJsonMsg = jsonObject.toJSONString();
            
			System.out.println("Pub MSG: " + pubJsonMsg);
			
			int pubQoS = 0;
			MqttMessage message = new MqttMessage(pubJsonMsg.getBytes());
			message.setQos(pubQoS);
			message.setRetained(false);

			// Publish the message
			//System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
			MqttDeliveryToken token = null;
			try {
				// publish message to broker
				token = topic.publish(message);
				// Wait until the message has been delivered to the broker
				token.waitForCompletion();
				Thread.sleep(500);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}			

		// disconnect
		try {
			myClient.disconnect();
			System.out.println("Close Connection");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
