package cir.csn.test.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cir.csn.server.snm.db.DAOFactory;
import cir.csn.server.snm.db.SensorNetworkMetaDAO;

public class SensorNetworkMetaDAOTest {
	private SensorNetworkMetaDAO sensorNetworkMetaDAO = new DAOFactory().sensorNetworkMetaDAO();
	
	
	@Before
    public void setUp() {
        System.out.println("@Before - setUp");
    }
 
    @After
    public void tearDown() {
    	System.out.println("@After - tearDown");
    }

	@Test
	public void getSensorNetworksSetTest() {
		Set<String> sensorNetworkSet = null;
		try {
			sensorNetworkSet = sensorNetworkMetaDAO.getSensorNetworksSet();
			
			Iterator<String> iter = sensorNetworkSet.iterator();
			
			while(iter.hasNext()) {
				String sn_name = (String) iter.next();
				System.out.println("SN Name: " +sn_name);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getSensorMembersSetTest() {
		Set<String> sensorMembersSet = null;
		try {
			sensorMembersSet = sensorNetworkMetaDAO.getSensorMembersSet("CSN.ODD.NET");
			
			Iterator<String> iter = sensorMembersSet.iterator();
			
			while(iter.hasNext()) {
				String snsr_uri = (String) iter.next();
				System.out.println("Sensor Members: " +snsr_uri);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
