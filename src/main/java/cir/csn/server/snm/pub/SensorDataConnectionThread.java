package cir.csn.server.snm.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cir.csn.server.snm.subs.SensorData;

public class SensorDataConnectionThread extends Thread {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private InputStream inputStream = null;
	private ObjectInputStream objectInputStream = null;
	SensorData sensorData;
	
	static final int PORT = 50002;

	public void run() {
		try {
			createServerSocketandStream();
			for(;;){
				SensorData tempSensorData = (SensorData) objectInputStream.readObject();
				
				if(tempSensorData.getTimestamp().compareTo("END") == 0)
					break;
				
				System.out.println("Sensor Data Received: " + tempSensorData.getSnsr_id());
				sensorData = new SensorData(tempSensorData.getSnsr_id(), tempSensorData.getTimestamp(), tempSensorData.getValue());
				sensorData.setSnsr_id(tempSensorData.getSnsr_id());
				sensorData.setTimestamp(tempSensorData.getTimestamp());
				sensorData.setValue(tempSensorData.getValue());
				SensorNetworkDataPublisher.getSensorDataQueue().offer(sensorData);
				System.out.println("Queue Size: " + SensorNetworkDataPublisher.getSensorDataQueue().size());
			}
			closeServerSocketandStream();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void createServerSocketandStream() throws IOException {
		try {		
			serverSocket = new ServerSocket(PORT);
			System.out.println("Waiting for client's connection with port num " + PORT);
			socket = serverSocket.accept();
			System.out.println("Accepted from " + socket.getInetAddress());
			/*System.out.println("Finish Client Creation");*/
		} catch (IOException e) { 
			throw e;
		}
		inputStream = socket.getInputStream();
		objectInputStream = new ObjectInputStream(inputStream);
		/*System.out.println("Finish Stream Creation");*/
	}

	public void closeServerSocketandStream()  throws IOException {
		try {	
			objectInputStream.close();
			inputStream.close();
			socket.close();
			serverSocket.close();
		} catch (IOException e) { 
			throw e;
		}
	}
}
