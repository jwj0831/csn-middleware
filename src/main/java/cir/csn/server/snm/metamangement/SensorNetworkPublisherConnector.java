package cir.csn.server.snm.metamangement;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

public class SensorNetworkPublisherConnector {
	private Socket socket = null;
	private OutputStream outputStream = null;
	private ObjectOutputStream objectOutputStream = null;
	private SensorNetworkMetadata snMetadata = null;
	static final String IP_ADDR = "localhost";
	static final int PORT = 50001;

	public void setSensorNetworkData(String sn_name, int sn_id, Set<String> sensors, boolean isRemove) {
		snMetadata = new SensorNetworkMetadata(sn_name, sn_id, sensors, isRemove);
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
		/*System.out.println("Stream Creation Completion");*/	
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
			/*System.out.println("Before send");
			System.out.println("SN Name: " + metadata.getSn_name() + " ID: " + metadata.getSn_id());*/
			
			objectOutputStream.reset();
			objectOutputStream.writeObject(snMetadata);
			objectOutputStream.flush();
			System.out.println("Finish Send Sensor Metadata object");
			//closeSocketandStream();
		} catch (IOException e) {
			System.out.println("Socket Open & Close Error!");
			e.printStackTrace();
		}
	}

}
