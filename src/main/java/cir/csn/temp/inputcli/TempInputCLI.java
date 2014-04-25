package cir.csn.temp.inputcli;

import java.sql.SQLException;
import java.util.Scanner;

import cir.csn.server.snm.db.DAOFactory;

public class TempInputCLI {
	private static SensorMetaDAO dao;
	private static Scanner sc;
	
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		dao = new DAOFactory().sensorMetaDAO();
		
		int commandType = 0;
		boolean continue_flag = true;

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
			System.out.println("8. Delete Sensor Network");
			System.out.print("Choose Command: ");
			commandType = sc.nextInt();
			sc.nextLine();
			
			switch(commandType){
			case 0:
				continue_flag = false;
				break;
			case 1:
				createSensorMeta();
				break;
			case 2:
				updateSensorMeta(); 
				break;
			case 3:
				deleteSensorMeta();
				break;
			case 4:
				createSensorTag();
				break;
			case 5:
				updateSensorTag();
				break;
			case 6:
				deleteSensorTag();
				break;
			case 7:
				createSensorNetwork();
				break;
			case 8:
				deleteSensorNetwork();
				break;
			default:
				System.out.println("Wrong Command");
				break;
			}
		}while(continue_flag);
		System.out.println("Bye");
	}

	private static void createSensorMeta() {
		String sensor_ip = null;
		String sensor_id = null;
		String sensor_uri = null;
		
		System.out.println("Create Sensor Meta Mode");
		System.out.print("Sensor IP: ");
		sensor_ip = sc.nextLine();
		System.out.println(sensor_ip);
		System.out.print("Sensor ID: ");
		sensor_id = sc.nextLine();
		System.out.println(sensor_id);
		sensor_uri = "http://" + sensor_ip + "/" + sensor_id;
		System.out.println("Your URI: " + sensor_uri);
		
		try {
			dao.addSensor(sensor_uri, sensor_ip, sensor_id);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Finish DB Input");
	}

	private static void updateSensorMeta() {
		System.out.println("Create Sensor Meta Mode");
	}

	private static void deleteSensorMeta() {
		System.out.println("Update Sensor Meta Mode");
	}

	private static void createSensorTag() {
		System.out.println("Create Sensor Tags Mode");
	}

	private static void updateSensorTag() {
		System.out.println("Update Sensor Tags Mode");
	}

	private static void deleteSensorTag() {
		System.out.println("Delete Sensor Tags Mode");
	}

	private static void createSensorNetwork() {
		System.out.println("Create Sensor Network Mode");
	}
	
	private static void deleteSensorNetwork() {
		System.out.println("Delete Sensor Network Mode");
	}
}
