package net.milkycraft.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.milkycraft.Utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConsoleWindow extends JDialog {

	private static final long serialVersionUID = -3649846404393057735L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea = new JTextArea();

	public ConsoleWindow() {
		setAlwaysOnTop(true);
		setTitle("Player Count Log");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane);
			{
				
				textArea.setEditable(false);
				scrollPane.setViewportView(textArea);
				for(String str : Utility.getLog()) {
					textArea.append(str + "\n");
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				{
					JButton btnRefresh = new JButton("Refresh");
					btnRefresh.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							textArea.setText("");
							for(String str : Utility.getLog()) {
								textArea.append(str + "\n");
							}
						}
					});
					buttonPane.add(btnRefresh);
				}
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

}
