package cir.csn.server.snm.db;

import cir.csn.temp.inputcli.SensorMetaDAO;

public class DAOFactory {
	public SensorDataDAO sensorDataDAO() {
		return new SensorDataDAO(connectionMaker());
	}
	
	public SensorMetaDAO sensorMetaDAO() {
		return new SensorMetaDAO(connectionMaker());
	}

	private ConnectionMaker connectionMaker() {
		return new JDBCConnectionMaker();
	}
}