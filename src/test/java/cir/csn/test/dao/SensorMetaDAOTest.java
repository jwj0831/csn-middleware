package cir.csn.test.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cir.csn.server.snm.db.DAOFactory;
import cir.csn.server.snm.metamangement.SensorMetaDAO;

public class SensorMetaDAOTest {
	SensorMetaDAO sensorMetaDAO = new DAOFactory().sensorMetaDAO();
	
	@Before
    public void setUp() {
        System.out.println("@Before - setUp");
    }
 
    @After
    public void tearDown() {
    }

	@Test
	public void getSensorMemebersTest() {
		
		
	}
}
