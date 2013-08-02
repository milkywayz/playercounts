package net.milkycraft;

import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.milkycraft.windows.ConfigWindow;
import net.milkycraft.windows.ConsoleWindow;

public class Utility {
	private static FontRenderContext frc = new FontRenderContext(null, true, true);
	private static TrayIcon current;
	private static SystemTray st = SystemTray.getSystemTray();
	public static PopupMenu pop;
	private static Menu m;
	private static ActionListener listener;
	private static List<String> logs = new ArrayList<String>();
	private static SimpleDateFormat sdf = new SimpleDateFormat("H:m:ss");

	public static BufferedImage getImage(String text) {
		Font f = Main.m.getConfig().getFont(Font.PLAIN);
		Rectangle2D bounds = f.getStringBounds(text, frc);
		int w = (int) bounds.getWidth();
		int h = (int) bounds.getHeight();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(255, 255, 255, 0));
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		g.setFont(f);
		RenderingHints rh = new RenderingHints(KEY_TEXT_ANTIALIASING,
				VALUE_TEXT_ANTIALIAS_GASP);
		g.setRenderingHints(rh);
		g.drawString(text, (float) bounds.getX(), (float) -bounds.getY());
		g.dispose();
		return image;
	}

	public static void init() {
		Config c = Main.m.getConfig();
		pop = new PopupMenu();
		MenuItem config = new MenuItem("Config");
		config.setShortcut(new MenuShortcut(KeyEvent.VK_C));
		MenuItem quit = new MenuItem("Quit");
		quit.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
		MenuItem log = new MenuItem("Log");
		log.setShortcut(new MenuShortcut(KeyEvent.VK_L));				
		final String tit = "Connected to " + c.getAddress() + ":"+ c.getPort();
		m = new Menu(tit);
		m.add("Player Count: 0");
		pop.add(m);
		pop.addSeparator();
		pop.add(new MenuItem("File version: " + UpdateCheck.CURRENT_VERSION));
		pop.addSeparator();
		pop.add(config);
		pop.add(log);
		pop.add(quit);
		listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == null) {
					return;
				}
				if (e.getActionCommand().equals("Config")) {
					ConfigWindow cmenu = new ConfigWindow(Main.m.getConfig());
					cmenu.setVisible(true);
					Utility.log("Config window opened");
				} else if (e.getActionCommand().equals("Log")) {
					ConsoleWindow lmenu = new ConsoleWindow();
					lmenu.setVisible(true);
				} else if (e.getActionCommand().equals("Quit")) {
					System.exit(0);
				}
			}
		};
		config.addActionListener(listener);
		log.addActionListener(listener);
		quit.addActionListener(listener);
	}

	public static void log(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String dets = sw.toString();
		logs.add(getDate() + "\n" + dets);
	}
	
	public static void log(String t) {
		logs.add(getDate() + t);
	}
	
	private static String getDate() {
		return "[" + sdf.format(new Date()) + "] ";
	}
	
	public static List<String> getLog() {
		return logs;
	}

	public static void update(int x) {
		final String tit = "Connected to " + Main.m.getConfig().getAddress() + ":" + Main.m.getConfig().getPort();
		Utility.log(tit);
		MenuItem menu = pop.getItem(0);
		menu.setLabel(tit);
		m.getItem(0).setLabel("Player Count: " + x);
		current.setPopupMenu(pop);
	}

	public static void setTray(int x) throws AWTException {
		if (SystemTray.isSupported()) {			
			if (current == null) {
				current = new TrayIcon(Main.m.cache[x], "Player Count", pop);
			}
			update(x);
			st.remove(current);
			current.setImage(Main.m.cache[x]);
			current.addActionListener(listener);
			st.add(current);
		} else {
			System.out.println("Could not set tray to " + x + "! Not supported");
		}
	}	
}
