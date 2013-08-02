package net.milkycraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ConfigUtility {
	private final static File config = new File("config.json");

	public static boolean hasConfig() {
		return config.exists();
	}

	@SuppressWarnings("unchecked")
	public static void saveDefault() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(config));
			JSONObject obj = new JSONObject();
			obj.put("Address", new String("ping.milkycraft.net"));
			obj.put("Port", new Integer(25565));
			obj.put("Refresh", new Integer(15));
			obj.put("Preload", new Integer(100));
			obj.put("Font", new String("Tahoma"));
			obj.put("Font-Size", new Integer(50));
			obj.put("Ping-Mode", new String("Custom"));
			obj.writeJSONString(out);
			out.close();
		} catch (Exception ex) {
			Utility.log(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static void saveConfig(Config c) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(config));
			JSONObject obj = new JSONObject();
			obj.put("Address", c.getAddress());
			obj.put("Port", c.getPort());
			obj.put("Refresh", c.getRefresh());
			obj.put("Preload", c.getPreload());
			obj.put("Font", c.getFont());
			obj.put("Font-Size", c.getFontSize());
			obj.put("Ping-Mode", c.getPingMode());
			obj.writeJSONString(out);
			out.close();
		} catch (Exception ex) {
			Utility.log(ex);
		}
	}

	public static void loadConfig(Config config) {
		try {
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(ConfigUtility.config));
			JSONObject json = (JSONObject) obj;
			config.setAddress((String) json.get("Address"));
			config.setPingMode((String) json.get("Ping-Mode"));
			config.setPort(((Long) json.get("Port")).intValue());
			config.setRefresh(((Long) json.get("Refresh")).intValue());
			config.setPreload(((Long) json.get("Preload")).intValue());
			config.setFont((String) json.get("Font"));
			config.setFontSize(((Long) json.get("Font-Size")).intValue());
		} catch (Exception ex) {
			Utility.log(ex);
		}
	}
}
