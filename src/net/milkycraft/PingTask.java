package net.milkycraft;

import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.TimerTask;

import ch.jamiete.mcping.MinecraftPing;
import ch.jamiete.mcping.MinecraftPingReply;

public class PingTask extends TimerTask {

	private MinecraftPing ping = new MinecraftPing();
	private Config c;

	public PingTask(Config config) {
		this.c = config;
	}

	@Override
	public void run() {
		try {
			if (c.getPingMode().equalsIgnoreCase("Custom")) {
				Socket socket = new Socket();
				socket.connect(new InetSocketAddress(c.getAddress(), c.getPort()), 3000);
				DataInputStream in = new DataInputStream(socket.getInputStream());
				Utility.setTray(in.read());
			} else {
				MinecraftPingReply mpr = ping.getPing(c.getAddress(), c.getPort());
				Utility.setTray(mpr.getOnlinePlayers());
			}
		} catch (Exception e) {
			Utility.log(e);
			try {
				Utility.setTray(c.getPreload() + 1);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(0);
			}
		}
	}
}
