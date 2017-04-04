package com.starterkit.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

@Component
public class Utils {

	public String getVcapServiceName(String vcap_service) {
		JSONParser jsonParser = new JSONParser();
		JSONObject bluePrintObject;
		String serviceName = null;
		try {
			bluePrintObject = (JSONObject) jsonParser.parse(vcap_service);
			for (Object key : bluePrintObject.keySet()) {
				if (key.toString().toLowerCase().contains("rabbit")) {
					JSONArray obj = (JSONArray) bluePrintObject.get(key.toString());
					JSONObject object = (JSONObject) obj.get(0);
					serviceName = (String) object.get("name");
					System.out.println(serviceName + "      djkjdkjdkjdkjdkd");
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serviceName;

	}

	public String getVcapAppName(String vcap_app) {
		String appName = null;
		JSONParser jsonParser = new JSONParser();
		JSONObject bluePrintObject;
		try {
			bluePrintObject = (JSONObject) jsonParser.parse(vcap_app);
			appName = (String) bluePrintObject.get("application_name");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return appName;
	}

}
