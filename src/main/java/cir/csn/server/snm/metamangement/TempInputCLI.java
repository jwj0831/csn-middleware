package cir.csn.server.snm.metamangement;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import cir.csn.server.snm.db.DAOFactory;
import cir.csn.server.snm.db.SensorMetaDAO;
import cir.csn.server.snm.db.SensorNetworkMetaDAO;

public class TempInputCLI {
	private SensorNetworkPublisherConnector snConnector = null;
	private SensorMetaDAO sensorMetaDAO;
	private SensorNetworkMetaDAO sensorNetworkMetaDAO;
	private Scanner sc;
	
	
	public TempInputCLI() {
		snConnector = new SensorNetworkPublisherConnector();
		sensorMetaDAO = new DAOFactory().sensorMetaDAO();
		sensorNetworkMetaDAO = new DAOFactory().sensorNetworkMetaDAO();
		sc = new Scanner(System.in);
	}

	public static void main(String[] args) {
		TempInputCLI cli = new TempInputCLI();
		cli.startCLIInterface();
	}

	private void startCLIInterface() {
		int commandType = 0;
		boolean continue_flag = true;
		
		try {
			snConnector.createSocketandStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		do {
			System.out.println("----Command Choose----");
			System.out.println("0. Log out");
			System.out.println("1. Create Sensor Meta");
			System.out.println("2. Update Sensor Meta");
			System.out.println("3. Delete Sensor Meta");
			System.out.println("4. Create Sensor Tags");
			System.out.println("5. Update Sensor Tags");
			System.out.println("6. Delete Sensor Tags");
			System.out.println("7. Create Sensor Network");
			System.out.println("8. Update Sensor Network");
			System.out.println("9. Delete Sensor Network");
			System.out.print("Choose Command: ");
			commandType = sc.nextInt();
			sc.nextLine();

			switch(commandType){
			case 0:
				continue_flag = false;
				break;
			case 1:
				this.createSensorMeta();
				break;
			case 2:
				this.updateSensorMeta(); 
				break;
			case 3:
				this.deleteSensorMeta();
				break;
			case 4:
				this.createSensorTag();
				break;
			case 5:
				this.updateSensorTag();
				break;
			case 6:
				this.deleteSensorTag();
				break;
			case 7:
				this.createSensorNetwork();
				break;
			case 8:
				this.updateSensorNetwork();
				break;
			case 9:
				this.deleteSensorNetwork();
				break;
			default:
				System.out.println("Wrong Command");
				break;
			}
		}while(continue_flag);
		try {
			//Temporary Close Message sn_id == 999
			snConnector.setSensorNetworkData(null, 999, null, false);
			snConnector.transferSensorNetworkMeta();
			snConnector.closeSocketandStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Bye");
	}

	private void createSensorMeta() {
		String ip = null;
		String id = null;
		String uri = null;

		System.out.println("Create Sensor Meta Mode");
		System.out.print("Sensor IP: ");
		ip = sc.nextLine();
		System.out.println(ip);
		System.out.print("Sensor ID: ");
		id = sc.nextLine();
		System.out.println(id);
		uri = "http://" + ip + "/" + id;
		System.out.println("Your URI: " + uri);

		try {
			sensorMetaDAO.addSensorMeta(uri, ip, id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Finish DB Input");
	}

	private void updateSensorMeta() {
		String ip = null;
		String id = null;
		String uri = null;

		System.out.println("Update Sensor Meta Mode");

		Map<String, String> sensorMetaMap = null;

		System.out.print("Input Sensor ID: ");
		id = sc.nextLine();

		try {
			sensorMetaMap = sensorMetaDAO.getSensorMeta(id);


			ip = sensorMetaMap.get("ip");
			System.out.println("Current Sensor IP: " + ip);

			String old_id = id;

			System.out.println("Input Updated Sensor Meta ");
			System.out.print("Sensor IP: ");
			ip = sc.nextLine();
			System.out.println(ip);
			System.out.print("Sensor ID: ");
			id = sc.nextLine();
			System.out.println(id);
			uri = "http://" + ip + "/" + id;
			System.out.println("Your New URI: " + uri);

			sensorMetaDAO.updateSensorMeta(uri, ip, id, old_id);
			System.out.println("Finish DB Update");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void deleteSensorMeta() {
		String sensor_id = null;
		System.out.println("Delete Sensor Meta Mode");

		System.out.print("Input Sensor ID: ");
		sensor_id = sc.nextLine();

		try {
			sensorMetaDAO.deleteSensorMeta(sensor_id);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Finish Sensor Meta Delete");
	}

	private void createSensorTag() {
		String id = null;
		String uri = null;
		String tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Create Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		try {
			sensorMetaMap = sensorMetaDAO.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");

			System.out.print("Input Tag: ");
			tag = sc.nextLine();

			sensorMetaDAO.addTagInfo(uri, tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateSensorTag() {
		String uri = null;
		String id = null;
		String tag = null;
		String new_tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Update Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		System.out.print("Tag: ");
		tag = sc.nextLine();

		System.out.print("New Tag: ");
		new_tag = sc.nextLine();

		try {
			sensorMetaMap = sensorMetaDAO.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");
			
			sensorMetaDAO.updateTagInfo(uri, tag, new_tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void deleteSensorTag() {
		String uri = null;
		String id = null;
		String tag = null;
		Map<String, String> sensorMetaMap = null;

		System.out.println("Delete Sensor Tags Mode");
		System.out.print("Sensor ID: ");
		id = sc.nextLine();

		System.out.print("Tag: ");
		tag = sc.nextLine();

		try {
			sensorMetaMap = sensorMetaDAO.getSensorMeta(id);
			uri = sensorMetaMap.get("uri");
			
			sensorMetaDAO.deleteTagInfo(uri, tag);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createSensorNetwork() {
		String sn_name = null;
		String create_date = null;
		int sn_id = 0;
		String input_sn_id = null;
		Set<String> sn_uris = new HashSet<String>();
		System.out.println("Create Sensor Network Mode");
		System.out.print("Sensor Network Name: ");
		sn_name = sc.nextLine();
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        create_date = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        
        try {
			sensorNetworkMetaDAO.addSensorNetwork(sn_name, create_date);
			System.out.println("Finish Insert Sensor Network in DB");
			System.out.println("Add Sensors");
			
			for(;;){
				System.out.print("Input Sensor(for quit to type 'x'): ");
				input_sn_id = sc.nextLine();
				if( input_sn_id.compareTo("x") == 0 )
					break;
				else {
					String sn_uri = sensorMetaDAO.getSensorURI(input_sn_id);
					sn_uris.add(sn_uri);
				}
			}
			sn_id = sensorNetworkMetaDAO.getSensorNetworkID(sn_name);
			sensorNetworkMetaDAO.addSensorNetworkMembers(sn_id, sn_uris);
			snConnector.setSensorNetworkData(sn_name, sn_id, sn_uris, false);
			snConnector.transferSensorNetworkMeta();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}

	private void updateSensorNetwork() {
		String name = null;
		String new_name = null;
		
		System.out.println("Update Sensor Network Mode");
		System.out.print("Sensor Network Name: ");
		name = sc.nextLine();
		
		System.out.print("Sensor Network New Name: ");
		new_name = sc.nextLine();
        
        try {
        	sensorNetworkMetaDAO.updateSensorNetwork(name, new_name);
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Finish Update Sensor Network in DB");
	}
	
	private void deleteSensorNetwork() {
		String name = null;
		String delete_date = null;
		System.out.println("Delete Sensor Network Mode");

		System.out.print("Sensor Network Name: ");
		name = sc.nextLine();
		
		Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        delete_date = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
        
        try {
        	sensorNetworkMetaDAO.deleteSensorNetwork(name, delete_date);
			snConnector.setSensorNetworkData(name, 0, null, true);
			snConnector.transferSensorNetworkMeta();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Finish Delet Sensor Network in DB");
		
	}
}
