package cir.csn.temp.inputcli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import cir.csn.server.snm.db.ConnectionMaker;

public class SensorMetaDAO {
	private ConnectionMaker connectionMaker;
	
	public SensorMetaDAO(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	
	public void addSensor(String uri, String ip, String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("INSERT INTO csn_snsr_meta(snsr_uri, snsr_ip, snsr_id) VALUES(?, ?, ?)");
		ps.setString(1, uri);
		ps.setString(2, ip);
		ps.setString(3, id);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	
}
