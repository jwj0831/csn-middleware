package cir.csn.server.snm.pub;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import cir.csn.server.snm.subs.SensorData;

public class SensorNetworkDataPublisher {
	private SensorNetworkMetaConnectionThread metaConnThread = null;
	private SensorDataConnectionThread snsrDataConnThread = null;
	private SensorNetworkDataPublishThread pubThread = null;
	private static Queue<SensorData> sensorDataQueue = null;
	
	public static Queue<SensorData> getSensorDataQueue() {
		if(sensorDataQueue == null) {
			sensorDataQueue = new LinkedList<SensorData>();
		}

		return sensorDataQueue;
	}
	
	public SensorNetworkDataPublisher() {
		this.metaConnThread = new SensorNetworkMetaConnectionThread();
		this.snsrDataConnThread = new SensorDataConnectionThread();
		this.pubThread = new SensorNetworkDataPublishThread();
	}
	
	public static void main(String[] args) {
		SensorNetworkDataPublisher pub = new SensorNetworkDataPublisher();
		Set<String> networkListSet = new HashSet<String>();
		Map<String, Set<String>> networkListMap = new HashMap<String, Set<String>>();
		
		pub.metaConnThread.setNetworkListSet(networkListSet);
		pub.pubThread.setNetworkListSet(networkListSet);
		
		pub.metaConnThread.setNetworkListMap(networkListMap);
		pub.pubThread.setNetworkListMap(networkListMap);
		
		System.out.println("Start Data Receive Thread...");
		pub.metaConnThread.start();
		
		System.out.println("Start SensorData Receive Thread...");
		pub.snsrDataConnThread.start();
		
		System.out.println("Start Data Pub Thread...");
		pub.pubThread.start();
	}
}
