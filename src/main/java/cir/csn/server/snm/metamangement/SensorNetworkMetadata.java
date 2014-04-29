package cir.csn.server.snm.metamangement;

import java.io.Serializable;
import java.util.Set;

public class SensorNetworkMetadata implements Serializable {
	private static final long serialVersionUID = 2653143741513138314L;
	private String sn_name;
	private int sn_id;
	private Set<String> sensors;
	private boolean isRemove;
	
	public SensorNetworkMetadata(String sn_name, int sn_id, Set<String> sensors, boolean isRemove) {
		this.sn_name = sn_name;
		this.sn_id = sn_id;
		this.sensors = sensors;
		this.isRemove = isRemove;
	}
	
	public String getSn_name() {
		return sn_name;
	}
	public void setSn_name(String sn_name) {
		this.sn_name = sn_name;
	}
	public int getSn_id() {
		return sn_id;
	}
	public void setSn_id(int sn_id) {
		this.sn_id = sn_id;
	}
	public Set<String> getSensors() {
		return sensors;
	}
	public void setSensors(Set<String> sensors) {
		this.sensors = sensors;
	}
	public boolean isRemove() {
		return isRemove;
	}
	public void setRemove(boolean isRemove) {
		this.isRemove = isRemove;
	}
}
