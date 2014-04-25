package cir.csn.temp.inputcli;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
	
	public Map<String, String> getSensorMeta(String id) throws ClassNotFoundException, SQLException {
		Map<String, String> sensorMetaMap = new HashMap<String, String>();
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("SELECT * FROM csn_snsr_meta WHERE snsr_id = ?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();

		rs.next();
		
		sensorMetaMap.put("uri", rs.getString("snsr_uri"));
		sensorMetaMap.put("ip", rs.getString("snsr_ip"));
		sensorMetaMap.put("id", rs.getString("snsr_id"));
		
		rs.close();
		ps.close();
		c.close();
		
		return sensorMetaMap;
	}
	
	public void updateSensorMeta(String uri, String ip, String id, String old_id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("UPDATE csn_snsr_meta SET snsr_uri=?, snsr_ip=?, snsr_id=? WHERE snsr_id=?");
		ps.setString(1, uri);
		ps.setString(2, ip);
		ps.setString(3, id);
		ps.setString(4, old_id);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void deleteSensorMeta(String id) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("DELETE FROM csn_snsr_meta WHERE snsr_id=?");
		ps.setString(1, id);
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public void addTagInfo(String uri, String tag) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("INSERT INTO csn_snsr_tags(snsr_uri, snsr_tag) VALUES(?, ?)");
		ps.setString(1, uri);
		ps.setString(2, tag);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void updateTagInfo(String uri, String tag, String new_tag) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("UPDATE csn_snsr_tags SET snsr_tag=? WHERE snsr_uri=? AND snsr_tag=?");
		ps.setString(1, new_tag);
		ps.setString(2, uri);
		ps.setString(3, tag);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void deleteTagInfo(String uri, String tag) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("DELETE FROM csn_snsr_tags WHERE snsr_uri=? AND snsr_tag=?");
		ps.setString(1, uri);
		ps.setString(2, tag);
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
}
