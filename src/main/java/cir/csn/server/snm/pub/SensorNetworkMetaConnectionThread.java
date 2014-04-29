package cir.csn.server.snm.pub;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import cir.csn.server.snm.metamangement.SensorNetworkMetadata;

public class SensorNetworkMetaConnectionThread extends Thread {
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private InputStream inputStream = null;
	private ObjectInputStream objectInputStream = null;
	private SensorNetworkMetadata metadata;
	private Set<String> networkListSet = null;
	private Map<String, Set<String>> networkListMap = null;

	static final int PORT = 50001;

	public void run() {
		try {
			createServerSocketandStream();
			for(;;){
				metadata = (SensorNetworkMetadata) objectInputStream.readObject();
				if(metadata.isRemove() == true) {
					networkListSet.remove(metadata.getSn_name());
					networkListMap.remove(metadata.getSn_name());
				}
				else if(metadata.getSn_id() == 999){
					break;	//Temporary Close Message sn_id == 999
				}
				else {
					System.out.println("Metadata Receive: " + metadata.getSn_name());
					networkListSet.add(metadata.getSn_name());
					networkListMap.put(metadata.getSn_name(), metadata.getSensors());
				}
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


	public void setNetworkListSet(Set<String> networkListSet) {
		this.networkListSet = networkListSet;
	}

	public void setNetworkListMap(Map<String, Set<String>> networkListMap) {
		this.networkListMap = networkListMap;
	}

}
