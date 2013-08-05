package net.milkycraft;

import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_GASP;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.io.StringWriter;

import net.milkycraft.windows.AlertWindow;
import net.milkycraft.windows.ConfigWindow;

public class Utility {
	private static FontRenderContext frc = new FontRenderContext(null, true, true);
	private static TrayIcon current;
	private static SystemTray st = SystemTray.getSystemTray();
	public static PopupMenu pop;
	private static Menu conf;
	private static ActionListener listener;

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
		conf = new Menu("Config");
		MenuItem quit = new MenuItem("Quit");
		quit.setShortcut(new MenuShortcut(KeyEvent.VK_Q));
		conf.add("IP: " + c.getAddress() + ":" + c.getPort());
		conf.add("Mode: " + c.getPingMode());
		conf.add("Font: " + c.getFont() + " Size: " + c.getFontSize());
		conf.add("Refresh: " + c.getRefresh() + " Preload: " + c.getPreload());	
		pop.add(conf);
		pop.addSeparator();
		pop.add(new MenuItem("File version: " + UpdateCheck.CURRENT_VERSION));	
		pop.add(quit);
		listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Config")) {
					ConfigWindow cmenu = new ConfigWindow(Main.m.getConfig());
					cmenu.setVisible(true);
				} else if (e.getActionCommand().equals("Quit")) {
					System.exit(0);
				}
			}
		};
		conf.addActionListener(listener);
		quit.addActionListener(listener);
	}

	public static void log(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		String dets = sw.toString();
		AlertWindow aw = new AlertWindow();
		aw.setTitle("Exception");
		aw.getTextArea().setText(dets);
		aw.getTextArea().setEditable(false);
	}

	public static void update() {
		Config c = Main.m.getConfig();
		conf = new Menu("Config");
		conf.add("IP: " + c.getAddress() + ":" + c.getPort());
		conf.add("Mode: " + c.getPingMode());
		conf.add("Font: " + c.getFont() + " Size: " + c.getFontSize());
		conf.add("Refresh: " + c.getRefresh() + " Preload: " + c.getPreload());
		pop.remove(0);
		pop.insert(conf, 0);
		current.setPopupMenu(pop);
	}

	public static void setTray(int x) {
		if (SystemTray.isSupported()) {
			if (current == null) {
				current = new TrayIcon(Main.m.cache[x], "Player Count", pop);
			}
			st.remove(current);
			current.setImage(Main.m.cache[x]);
			current.addActionListener(listener);
			try {
				st.add(current);
			} catch (AWTException e) {
				Utility.log(e);
			}
		} else {
			Utility.log(new AWTException("System tray not supported"));
		}
	}
}
