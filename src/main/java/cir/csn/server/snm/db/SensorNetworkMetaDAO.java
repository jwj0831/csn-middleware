package cir.csn.server.snm.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SensorNetworkMetaDAO {
	private ConnectionMaker connectionMaker;

	public SensorNetworkMetaDAO(ConnectionMaker connectionMaker){
		this.connectionMaker = connectionMaker;
	}
	
	public void addSensorNetwork(String name, String create_date) throws ClassNotFoundException, SQLException { 
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("INSERT INTO csn_sn_meta(sn_name, sn_create_date) VALUES(?, ?)");
		ps.setString(1, name);
		ps.setString(2, create_date);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void updateSensorNetwork(String name, String new_name ) throws ClassNotFoundException, SQLException { 
		Connection c = connectionMaker.makeConnection();

		PreparedStatement ps = c.prepareStatement("UPDATE csn_sn_meta SET sn_name=? WHERE sn_name=?");
		ps.setString(1, new_name);
		ps.setString(2, name);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public void deleteSensorNetwork(String name, String delete_date) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		PreparedStatement ps = c.prepareStatement("UPDATE csn_sn_meta SET sn_life=?, sn_delete_date=? WHERE sn_name=?");
		ps.setInt(1, 0);
		ps.setString(2, delete_date);
		ps.setString(3, name);
		ps.executeUpdate();

		ps.close();
		c.close();
	}
	
	public int getSensorNetworkID(String name)  throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		int sn_id = 0;
		PreparedStatement ps = c.prepareStatement("SELECT sn_id FROM csn_sn_meta WHERE sn_name = ?");
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		rs.next();
		sn_id = rs.getInt("sn_id");

		rs.close();
		ps.close();
		c.close();
		
		return sn_id;
	}
	
	public Set<String> getSensorNetworksSet() throws ClassNotFoundException, SQLException {
		Set<String> sensorNetworkSet = new HashSet<String>();
		Connection c = connectionMaker.makeConnection();
		
		PreparedStatement ps = c.prepareStatement("SELECT sn_name FROM csn_sn_meta");
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			sensorNetworkSet.add(rs.getString("sn_name"));
		}

		rs.close();
		ps.close();
		c.close();
		
		return sensorNetworkSet;
	}
	
	public Set<String> getSensorMembersSet(String sn_name) throws ClassNotFoundException, SQLException {
		Set<String> sensorMembersSet = new HashSet<String>();
		Connection c = connectionMaker.makeConnection();
		int sn_id = this.getSensorNetworkID(sn_name);
		
		PreparedStatement ps = c.prepareStatement("SELECT sn_member FROM csn_sn_members WHERE sn_id = ?");
		ps.setInt(1, sn_id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()) {
			sensorMembersSet.add(rs.getString("sn_member"));
		}

		rs.close();
		ps.close();
		c.close();
		
		return sensorMembersSet;
	}
	
	public void addSensorNetworkMembers(int sn_id, Set<String> sensors) throws ClassNotFoundException, SQLException {
		Connection c = connectionMaker.makeConnection();
		Iterator<String> iter = sensors.iterator();
		PreparedStatement ps = c.prepareStatement("INSERT INTO csn_sn_members(sn_id, sn_member) VALUES(?, ?)");
		
		while(iter.hasNext()){
			String snsr_uri= iter.next();
			ps.setInt(1, sn_id);
			ps.setString(2, snsr_uri);
			ps.executeUpdate();
		}
		
		ps.close();
		c.close();
	}

}
