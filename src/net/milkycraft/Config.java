package net.milkycraft;

import java.awt.Font;

public class Config {

	private String address = "";
	private String pingMode = "";
	private String font = "";
	private int port = 0;
	private int refresh = 0;
	private int fontSize = 0;
	private int preload = 0;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPingMode() {
		return pingMode;
	}

	public void setPingMode(String pingMode) {
		this.pingMode = pingMode;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getRefresh() {
		return refresh;
	}

	public void setRefresh(int refresh) {
		this.refresh = refresh;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getPreload() {
		return preload;
	}

	public void setPreload(int preload) {
		this.preload = preload;
	}
	
	public Font getFont(int type) {
		return new Font(font, type, fontSize);
	}
}
