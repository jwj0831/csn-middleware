package cir.csn.server.snm.subs;

import java.io.Serializable;

public class SensorData implements Serializable {
	private static final long serialVersionUID = -7180355001007457312L;
	private String snsr_id;
	private String timestamp;
	private String value;
	
	public SensorData (String snsr_id, String timestamp, String value) {
		this.snsr_id = snsr_id;
		this.timestamp = timestamp;
		this.value = value;
	}
	
	public String getSnsr_id() {
		return snsr_id;
	}
	public void setSnsr_id(String snsr_id) {
		this.snsr_id = snsr_id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
