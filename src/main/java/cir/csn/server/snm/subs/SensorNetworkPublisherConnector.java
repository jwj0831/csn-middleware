package cir.csn.server.snm.subs;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

public class SensorNetworkPublisherConnector {
	private Socket socket = null;
	private OutputStream outputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private SensorData sensorData = null;
	static final String IP_ADDR = "localhost";
	static final int PORT = 50002;

	public void setSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}

	public void createSocketandStream() throws IOException {
		try {		
			socket=new Socket(IP_ADDR, PORT);
			System.out.println("Socket Creation Completion");			
		} catch (IOException e) { 
			throw e;
		}
		outputStream = socket.getOutputStream();
		objectOutputStream = new ObjectOutputStream(outputStream);
		System.out.println("Stream Creation Completion");	
	}

	public void closeSocketandStream()  throws IOException {
		try {	
			objectOutputStream.close();
			outputStream.close();
			socket.close();
		} catch (IOException e) { 
			throw e;
		}
	}

	public void transferSensorNetworkMeta() {
		try {
			//createSocketandStream();
			/*System.out.println("Before send");*/
			objectOutputStream.reset();
			objectOutputStream.writeObject(sensorData);
			objectOutputStream.flush();
			/*System.out.println("Finish Send Sensor data object");*/
			//closeSocketandStream();
		} catch (IOException e) {
			System.out.println("Socket Open & Close Error!");
			e.printStackTrace();
		}
	}

}
