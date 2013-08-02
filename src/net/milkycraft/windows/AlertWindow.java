package net.milkycraft.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AlertWindow extends JDialog {

	private static final long serialVersionUID = -6707221570022877056L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea = new JTextArea();

	public AlertWindow() {
		setAlwaysOnTop(true);
		setTitle("Update alert");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textArea.setBounds(6, 6, 438, 227);
		contentPanel.add(textArea);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}

	public void fill(String local, String remote, String readme, String url) {
		textArea.append("Your version: " + local + "\n");
		textArea.append("Current version: " + remote + "\n");
		textArea.append("Changes: " + readme + "\n");
		textArea.append("Download: " + url);
	}
}
