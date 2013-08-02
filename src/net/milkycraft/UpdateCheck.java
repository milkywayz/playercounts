package net.milkycraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.milkycraft.windows.AlertWindow;

public class UpdateCheck  {

	public final static String CURRENT_VERSION = "1.0";
	public final static boolean alerted = false;
	
	public static boolean needsUpdate() {
		try {
			URL url = new URL("http://milkycraft.net/api/pc/version.php");
			BufferedReader in = new BufferedReader(
					new InputStreamReader(url.openStream()));
			String str = in.readLine();
			String[] args = str.split("::");
			in.close();
			if (args[0].equals(CURRENT_VERSION)) {
				System.out.println("PlayerCount is up to date");
				return false;
			}
			UpdateCheck.alert(args[0], args[1], args[2]);
			return true;	
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	
	public static void alert(String nver, String read, String url) {
		AlertWindow aw = new AlertWindow();
		aw.fill(CURRENT_VERSION, nver, read, url);
		if(!alerted){
 		Utility.pop.insert("Update to " + nver + ", from " + url, 3);
		}
	}
	
}
