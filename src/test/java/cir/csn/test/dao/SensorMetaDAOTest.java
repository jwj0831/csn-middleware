package cir.csn.test.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cir.csn.server.snm.db.DAOFactory;
import cir.csn.server.snm.db.SensorMetaDAO;


public class SensorMetaDAOTest {
	SensorMetaDAO sensorMetaDAO = new DAOFactory().sensorMetaDAO();
	static final String snsr_uri = "http://localhost/TestNode";
	static final String snsr_ip = "localhost";
	static final String snsr_id = "TestNode";

	@Before
	public void setUp() {
		System.out.println("Before");
	}
	
	@After 
	public void tearDown(){
		System.out.println("Tear Down");
	}
	
	
	@Test
	public void addSensorMetaTest() {
		try {
			sensorMetaDAO.addSensorMeta(snsr_uri, snsr_ip, snsr_id);
			String test_uri = sensorMetaDAO.getSensorURI(snsr_id);
			
			assertEquals(snsr_uri, test_uri);
			sensorMetaDAO.deleteSensorMeta(snsr_id);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSensorMetaTest() {
		try {
			sensorMetaDAO.addSensorMeta(snsr_uri, snsr_ip, snsr_id);
			Map<String, String> sensorMeataMap = sensorMetaDAO.getSensorMeta(snsr_id);
			
			String test_uri = sensorMeataMap.get("uri");
			String test_ip = sensorMeataMap.get("ip");
			String test_id = sensorMeataMap.get("id");
			
			System.out.println("Test Result");
			System.out.println("Sensor URI: " + test_uri);
			System.out.println("Sensor IP: " + test_ip);
			System.out.println("Sensor ID: " + test_id);
			
			assertEquals(snsr_uri, test_uri);
			assertEquals(snsr_ip, test_ip);
			assertEquals(snsr_id, test_id);
			
			sensorMetaDAO.deleteSensorMeta(snsr_id);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
