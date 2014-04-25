package cir.csn.server.snm.subs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SensorDataParser {
	private String data;
	private JSONObject jsonObj;
	
	public SensorDataParser(String data){
		this.data = data;
		
		JSONParser jsonParser = new JSONParser();
		try {
			this.jsonObj = (JSONObject) jsonParser.parse(this.data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject getJsonObj() {
		return jsonObj;
	}

}
