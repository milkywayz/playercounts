package net.milkycraft.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import net.milkycraft.Config;
import net.milkycraft.ConfigUtility;
import net.milkycraft.Main;

public class ConfigWindow extends JDialog {

	private static final long serialVersionUID = -7039637442535121030L;
	private final JPanel contentPanel = new JPanel();
	private JTextField connaddress;
	private JTextField refresh;
	private JTextField fontfield;
	private JTextField fontsize;
	private JTextField connport;
	private JTextField preload;

	public ConfigWindow(Config c) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Config Menu");
		setModal(true);
		setAlwaysOnTop(true);
		setBounds(100, 100, 449, 192);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		connaddress = new JTextField();
		connaddress.setBounds(137, 17, 194, 28);
		contentPanel.add(connaddress);
		connaddress.setColumns(10);
		connaddress.setText(c.getAddress());

		refresh = new JTextField();
		refresh.setBounds(137, 58, 94, 28);
		contentPanel.add(refresh);
		refresh.setColumns(10);
		refresh.setText(String.valueOf(c.getRefresh()));

		fontfield = new JTextField();
		fontfield.setBounds(137, 38, 134, 28);
		contentPanel.add(fontfield);
		fontfield.setColumns(10);
		fontfield.setText(c.getFont());

		final JToggleButton tglbtnCustomPingMode = new JToggleButton("Custom Ping Mode");
		tglbtnCustomPingMode
				.setToolTipText("Toggle for use on other servers then Milkycraft");
		tglbtnCustomPingMode.setSelected(true);
		tglbtnCustomPingMode.setBounds(147, 98, 161, 29);
		contentPanel.add(tglbtnCustomPingMode);
		tglbtnCustomPingMode
				.setSelected(c.getPingMode().equalsIgnoreCase("Custom") ? true : false);

		JLabel lblConnectionAddress = new JLabel("Connection address");
		lblConnectionAddress.setBounds(6, 23, 134, 16);
		contentPanel.add(lblConnectionAddress);

		fontsize = new JTextField();
		fontsize.setBounds(265, 38, 46, 28);
		contentPanel.add(fontsize);
		fontsize.setColumns(10);
		fontsize.setText(String.valueOf(c.getFontSize()));

		JLabel lblFontName = new JLabel("Font Name / Size");
		lblFontName.setBounds(16, 44, 124, 16);
		contentPanel.add(lblFontName);

		connport = new JTextField();
		connport.setBounds(325, 17, 61, 28);
		contentPanel.add(connport);
		connport.setColumns(10);
		connport.setText(String.valueOf(c.getPort()));

		JLabel lblRefresh = new JLabel("Refresh / Preload");
		lblRefresh.setBounds(26, 64, 114, 16);
		contentPanel.add(lblRefresh);

		preload = new JTextField();
		preload.setBounds(225, 58, 74, 28);
		contentPanel.add(preload);
		preload.setColumns(10);
		preload.setText(String.valueOf(c.getPreload()));

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Config conf = new Config();
				conf.setAddress(connaddress.getText());
				conf.setPort(Integer.parseInt(connport.getText()));
				conf.setPreload(Integer.parseInt(preload.getText()));
				conf.setFont(fontfield.getText());
				conf.setFontSize(Integer.parseInt(fontsize.getText()));
				conf.setRefresh(Integer.parseInt(refresh.getText()));
				String pingmode = tglbtnCustomPingMode.isSelected() ? "Custom"
						: "Default";
				conf.setPingMode(pingmode);
				ConfigUtility.saveConfig(conf);
				Main.m.reload(conf);
				setVisible(false);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
			}
		});
		buttonPane.add(cancelButton);
	}
}
