package cir.csn.server.snm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cir.csn.server.snm.subs.SensorData;

public class SensorDataDAO {
	private ConnectionMaker connectionMaker;
	
	public SensorDataDAO(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	
	public void add(SensorData sensorData) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("INSERT INTO csn_snsr_data(snsr_uri, timestamp, val) VALUES(?, ?, ?)");
		ps.setString(1, sensorData.getSnsr_id());
		ps.setString(2, sensorData.getTimestamp());
		ps.setString(3, sensorData.getValue());
		ps.executeUpdate();

		ps.close();
		c.close();
	}

}
