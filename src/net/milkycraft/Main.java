package net.milkycraft;

public class Main {
	private Config config = new Config();
	private java.util.Timer u = new java.util.Timer(true);
	protected java.awt.Image[] cache;
	public static Main m;

	public static void main(String[] args) {
		m = new Main();
		m.launch();
	}

	public Main() {
		try {
			debug("Launching application");
			if (ConfigUtility.hasConfig()) {
				debug("Config found, loading");
				ConfigUtility.loadConfig(config);
			} else {
				debug("Config not found, generating and then loading");
				ConfigUtility.saveDefault();
				ConfigUtility.loadConfig(config);
			}			
		} catch (Exception e) {
			Utility.log(e);
		}
	}
	
	public void launch() {
		int a = config.getPreload();
		cache = new java.awt.Image[a + 3];
		for (int i = 0; i < a; i++) {
			cache[i] = Utility.getImage(String.valueOf(i));
		}
		cache[a + 1] = Utility.getImage("?");
		cache[a + 2] = Utility.getImage("!");
		Utility.init();
		debug("Starting process");
		start(config);
	}
	
	private void debug(String i) {
		System.out.println(i);
	}

	public void reload(Config c) {
		config = c;
		int a = config.getPreload();
		cache = new java.awt.Image[a + 3];
		for (int i = 0; i < a; i++) {
			cache[i] = Utility.getImage(String.valueOf(i));
		}
		cache[a + 1] = Utility.getImage("?");
		cache[a + 2] = Utility.getImage("!");
		stop();
		u = new java.util.Timer(true);
		start(c);
		Utility.update(0);
	}

	private void start(Config c) {
		u.scheduleAtFixedRate(new PingTask(c), 0, c.getRefresh() * 1000);
		u.scheduleAtFixedRate(new java.util.TimerTask() {

			@Override
			public void run() {
				if(UpdateCheck.needsUpdate()) {
					System.out.println("Update found");
				}
			}
			
		}, 5000, 300000);
	}

	public void stop() {
		u.cancel();
	}

	public Config getConfig() {
		return config;
	}
}
