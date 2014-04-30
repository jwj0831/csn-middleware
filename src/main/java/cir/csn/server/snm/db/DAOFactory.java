package cir.csn.server.snm.db;


public class DAOFactory {
	public SensorDataDAO sensorDataDAO() {
		return new SensorDataDAO(connectionMaker());
	}
	
	public SensorMetaDAO sensorMetaDAO() {
		return new SensorMetaDAO(connectionMaker());
	}
	
	public SensorNetworkMetaDAO sensorNetworkMetaDAO() {
		return new SensorNetworkMetaDAO(connectionMaker());
	}

	private ConnectionMaker connectionMaker() {
		return new JDBCConnectionMaker();
	}
}